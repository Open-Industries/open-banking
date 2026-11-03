package org.openindustries.banking.ledger;

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

@Path("/api/v1/ledger")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Ledger", description = "Double-entry general ledger")
public class LedgerResource {

    @Inject
    LedgerService service;

    @POST
    @Path("/journal-entries")
    @Operation(summary = "Book a balanced double-entry journal entry")
    public Response book(@Valid CreateJournalEntryRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.book(request)).build();
    }

    @GET
    @Path("/journal-entries/{id}")
    @Operation(summary = "Get one journal entry")
    public JournalEntry get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @GET
    @Path("/journal-entries")
    @Operation(summary = "List journal entries")
    public List<JournalEntry> list() {
        return service.list();
    }

    @GET
    @Path("/balances")
    @Operation(summary = "Trial balance of one account in one currency (credits minus debits)")
    public Map<String, Long> balance(@QueryParam("accountId") UUID accountId,
                                     @QueryParam("currency") String currency) {
        return Map.of("balanceMinor", service.balanceOf(accountId, currency));
    }
}
