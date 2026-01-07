package br.com.erickmarques.loan_manager.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PaymentRequest(
        @NotNull(message = "The field loan date is required.")
        LocalDate paymentDate,

        @NotNull(message = "The field amount is required.")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
        @Digits(integer = 16, fraction = 2)
        @Positive
        BigDecimal amount,
        String notes,

        @NotNull(message = "The field type is required.")
        PaymentType type,

        @NotNull(message = "The field loanId is required.")
        UUID loanId
) {}
