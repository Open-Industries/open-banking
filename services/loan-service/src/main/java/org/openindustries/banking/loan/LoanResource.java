package org.openindustries.banking.loan;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/v1/loans")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Loans", description = "Loan origination and amortization")
public class LoanResource {

    @Inject
    LoanService service;

    @POST
    @Operation(summary = "Apply for a loan")
    public Response apply(@Valid ApplyLoanRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.apply(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one loan")
    public Loan get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @GET
    @Operation(summary = "List loans of a customer")
    public List<Loan> byCustomer(@QueryParam("customerId") UUID customerId) {
        return service.byCustomer(customerId);
    }

    @POST
    @Path("/{id}/decision")
    @Operation(summary = "Approve or reject an applied loan")
    public Loan decide(@PathParam("id") UUID id, Map<String, Boolean> body) {
        return service.decide(id, Boolean.TRUE.equals(body.get("approved")));
    }

    @GET
    @Path("/{id}/schedule")
    @Operation(summary = "Amortization schedule (French system, minor units)")
    public List<Installment> schedule(@PathParam("id") UUID id) {
        return service.schedule(id);
    }
}
