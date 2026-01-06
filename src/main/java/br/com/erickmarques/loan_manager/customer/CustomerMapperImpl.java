package br.com.erickmarques.loan_manager.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponse toResponse(Customer customer,
                                       Long openLoans,
                                       Long closedLoans) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .notes(customer.getNotes())
                .quantityOpenLoans(openLoans)
                .quantityClosedLoans(closedLoans)
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    @Override
    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .name(request.name())
                .phone(request.phone())
                .notes(request.notes())
                .build();
    }

    @Override
    public Customer updateEntity(Customer existing, CustomerRequest request) {
        return existing.toBuilder()
                .name(request.name())
                .phone(request.phone())
                .notes(request.notes())
                .build();
    }
}
