package br.com.erickmarques.loan_manager.customer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CustomerRequest(
        @NotEmpty(message = "The field name is required.")
        String name,
        @NotEmpty(message = "The field phone is required.")
        String phone,
        String notes
) { }