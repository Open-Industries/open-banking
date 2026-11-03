package org.openindustries.banking.payment;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Payments", description = "Payment orders and transfers")
public class PaymentResource {

    @Inject
    PaymentService service;

    @POST
    @Operation(summary = "Create a payment order (requires Idempotency-Key)")
    public Response create(@HeaderParam("Idempotency-Key") String idempotencyKey,
                           @Valid CreatePaymentRequest request) {
        PaymentOrder order = service.create(idempotencyKey, request);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one payment order")
    public PaymentOrder get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @GET
    @Operation(summary = "List payment orders")
    public List<PaymentOrder> list() {
        return service.list();
    }

    @POST
    @Path("/{id}/authorize")
    @Operation(summary = "Authorize a created payment")
    public PaymentOrder authorize(@PathParam("id") UUID id) {
        return service.authorize(id);
    }

    @POST
    @Path("/{id}/execute")
    @Operation(summary = "Execute an authorized payment")
    public PaymentOrder execute(@PathParam("id") UUID id) {
        return service.execute(id);
    }
}
