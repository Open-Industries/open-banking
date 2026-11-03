package org.openindustries.banking.account;

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

@Path("/api/v1/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Accounts", description = "Deposit accounts")
public class AccountResource {

    @Inject
    AccountService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a deposit account")
    public Response open(@Valid OpenAccountRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one account")
    public Account get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @GET
    @Operation(summary = "List accounts of a customer")
    public List<Account> byCustomer(@QueryParam("customerId") UUID customerId) {
        return service.byCustomer(customerId);
    }

    @POST
    @Path("/{id}/block")
    @Operation(summary = "Block an account")
    public Account block(@PathParam("id") UUID id) {
        return service.setBlocked(id, true);
    }

    @POST
    @Path("/{id}/postings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Apply a ledger-confirmed posting (internal, event-driven in production)")
    public Account posting(@PathParam("id") UUID id, Map<String, Long> body) {
        return service.applyPosting(id, body.getOrDefault("deltaMinor", 0L));
    }
}
