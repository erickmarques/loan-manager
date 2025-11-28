package br.com.erickmarques.loan_manager.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PaymentRequest(
        LocalDate paymentDate,
        BigDecimal amount,
        String notes,
        PaymentType type,
        UUID loanId
) {}
