package org.openindustries.banking.customer;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/v1/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "Customer onboarding and KYC status")
public class CustomerResource {

    @Inject
    CustomerService service;

    @POST
    @Operation(summary = "Onboard a new customer")
    public Response onboard(@Valid CreateCustomerRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.onboard(request)).build();
    }

    @GET
    @Operation(summary = "List customers")
    public List<Customer> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one customer")
    public Customer get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/kyc-decision")
    @Operation(summary = "Record the KYC verification decision")
    public Customer kycDecision(@PathParam("id") UUID id, Map<String, Boolean> body) {
        return service.decideKyc(id, Boolean.TRUE.equals(body.get("verified")));
    }
}
