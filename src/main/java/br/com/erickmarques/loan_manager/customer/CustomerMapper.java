package br.com.erickmarques.loan_manager.customer;

public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer,
                                Long openLoans,
                                Long closedLoans);

    Customer toEntity(CustomerRequest request);

    Customer updateEntity(Customer existing, CustomerRequest request);
}