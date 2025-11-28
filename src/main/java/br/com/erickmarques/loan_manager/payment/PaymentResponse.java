package br.com.erickmarques.loan_manager.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PaymentResponse(
        UUID id,
        LocalDate paymentDate,
        BigDecimal amount,
        String notes,
        PaymentType type,
        UUID loanId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
