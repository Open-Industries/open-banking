package org.openindustries.banking.functions.kyc;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Evaluates the completeness of a KYC submission for the Customer Service.
 * It checks document presence and validity windows only — biometric/OCR verification
 * is delegated to a specialized provider behind the same contract.
 * The verdict feeds POST /api/v1/customers/{id}/kyc-decision.
 */
public class Function {

    public record KycSubmission(String customerId, boolean governmentIdPresent, boolean governmentIdExpired,
                                boolean proofOfAddressPresent, boolean selfiePresent) {
    }

    public record KycVerdict(String customerId, boolean verified, List<String> findings) {
    }

    @Funq
    public KycVerdict kycDocumentProcessor(KycSubmission submission) {
        List<String> findings = new ArrayList<>();
        if (!submission.governmentIdPresent()) {
            findings.add("government ID missing");
        } else if (submission.governmentIdExpired()) {
            findings.add("government ID expired");
        }
        if (!submission.proofOfAddressPresent()) {
            findings.add("proof of address missing");
        }
        if (!submission.selfiePresent()) {
            findings.add("liveness selfie missing");
        }
        return new KycVerdict(submission.customerId(), findings.isEmpty(), findings);
    }
}
