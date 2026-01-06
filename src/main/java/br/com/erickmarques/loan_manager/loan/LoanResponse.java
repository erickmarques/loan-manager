package br.com.erickmarques.loan_manager.loan;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LoanResponse(
        UUID id,
        LocalDate loanDate,
        LocalDate paymentDate,
        BigDecimal amount,
        BigDecimal percentage,
        BigDecimal totalAmountToPay,
        boolean negotiation,
        String notes,
        LoanStatus status,
        String customerName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
