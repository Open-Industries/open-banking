package org.openindustries.banking.card;

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
import java.util.UUID;

@Path("/api/v1/cards")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Cards", description = "Card issuance and lifecycle (masked PAN only)")
public class CardResource {

    @Inject
    CardService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Issue a card for an account")
    public Response issue(@Valid IssueCardRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.issue(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one card")
    public Card get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @GET
    @Operation(summary = "List cards of an account")
    public List<Card> byAccount(@QueryParam("accountId") UUID accountId) {
        return service.byAccount(accountId);
    }

    @POST
    @Path("/{id}/block")
    @Operation(summary = "Block a card")
    public Card block(@PathParam("id") UUID id) {
        return service.block(id);
    }
}
