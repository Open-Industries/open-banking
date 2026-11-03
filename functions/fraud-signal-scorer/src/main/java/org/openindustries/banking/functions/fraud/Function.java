package org.openindustries.banking.functions.fraud;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores a payment event with transparent, auditable rules and recommends
 * APPROVE (< 40), REVIEW (40-79) or BLOCK (>= 80). Rules are configuration-tunable
 * in a full deployment; the scoring rationale is returned for audit (ISO/IEC 27001 A.8.15 logging).
 */
public class Function {

    public record TransactionEvent(String paymentId, long amountMinor, String currency,
                                   int paymentsLastHour, boolean newPayee, boolean unusualHour) {
    }

    public record FraudScore(String paymentId, int score, String decision, String rationale) {
    }

    @Funq
    public FraudScore fraudSignalScorer(TransactionEvent event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.amountMinor() >= 1_000_000) { // >= 10,000.00 in minor units
            score += 35;
            why.append("high amount; ");
        }
        if (event.paymentsLastHour() >= 10) {
            score += 25;
            why.append("high velocity; ");
        }
        if (event.newPayee()) {
            score += 20;
            why.append("first payment to payee; ");
        }
        if (event.unusualHour()) {
            score += 20;
            why.append("unusual hour for this customer; ");
        }
        String decision = score >= 80 ? "BLOCK" : score >= 40 ? "REVIEW" : "APPROVE";
        return new FraudScore(event.paymentId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
