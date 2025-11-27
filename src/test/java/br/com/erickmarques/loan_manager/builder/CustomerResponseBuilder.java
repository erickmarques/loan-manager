package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.customer.CustomerResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerResponseBuilder {

    public static CustomerResponse createDefault() {
        return CustomerResponse.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .name("John Doe")
                .phone("123456789")
                .notes("Test notes")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
