package br.com.erickmarques.loan_manager.customer;

public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);

    Customer toEntity(CustomerRequest request);

    Customer updateEntity(Customer existing, CustomerRequest request);
}