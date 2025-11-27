package br.com.erickmarques.loan_manager.customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request);

    CustomerResponse update(UUID id, CustomerRequest request);

    CustomerResponse findById(UUID id);

    List<CustomerResponse> findAll();

    void deleteById(UUID id);
}
