package br.com.erickmarques.loan_manager.customer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CustomerResponse(
        UUID id,
        String name,
        String phone,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}