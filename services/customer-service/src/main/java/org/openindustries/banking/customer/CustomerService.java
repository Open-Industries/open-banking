package org.openindustries.banking.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository repository;

    public Customer onboard(CreateCustomerRequest request) {
        return repository.save(Customer.create(
                request.type(), request.fullName(), request.documentId(), request.email()));
    }

    public Customer get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Customer not found: " + id));
    }

    public List<Customer> list() {
        return repository.findAll();
    }

    /** KYC decision, normally driven by the kyc-document-processor function verdict. */
    public Customer decideKyc(UUID id, boolean verified) {
        Customer customer = get(id);
        customer.kycStatus = verified ? "VERIFIED" : "REJECTED";
        return repository.save(customer);
    }
}
