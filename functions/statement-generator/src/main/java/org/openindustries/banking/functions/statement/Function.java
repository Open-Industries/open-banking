package org.openindustries.banking.functions.statement;

import io.quarkus.funqy.Funq;

import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Triggered by an HTTP call or a CloudEvent when a statement period closes.
 * Input: the account's ledger postings for the period (from the Ledger Service).
 * Output: totals that a renderer turns into PDF/HTML statements.
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Posting(String direction, long amountMinor, String currency) {
    }

    public record StatementRequest(String accountId, String period, String currency, List<Posting> postings) {
    }

    public record StatementSummary(String accountId, String period, String currency,
                                   long totalCreditsMinor, long totalDebitsMinor, long netMinor, int postingCount) {
    }

    @Funq
    public StatementSummary statementGenerator(StatementRequest request) {
        long credits = 0;
        long debits = 0;
        for (Posting p : request.postings()) {
            if (!request.currency().equals(p.currency())) {
                continue; // one statement per currency
            }
            if ("CREDIT".equals(p.direction())) {
                credits += p.amountMinor();
            } else {
                debits += p.amountMinor();
            }
        }
        return new StatementSummary(request.accountId(), request.period(),
                request.currency(), credits, debits, credits - debits, request.postings().size());
    }
}
