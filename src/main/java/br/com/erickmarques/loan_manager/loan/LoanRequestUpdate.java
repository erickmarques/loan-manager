package br.com.erickmarques.loan_manager.loan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LoanRequestUpdate(

        @NotNull(message = "The field loan date is required.")
        LocalDate loanDate,

        LocalDate paymentDate,

        @NotNull(message = "The field amount is required.")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
        @Digits(integer = 16, fraction = 2)
        @Positive
        BigDecimal amount,

        @NotNull(message = "The field totalAmountToPay is required.")
        @DecimalMin(value = "0.01", message = "Total amount to pay must be greater than zero.")
        @Digits(integer = 16, fraction = 2)
        BigDecimal totalAmountToPay,

        @NotNull(message = "The field percentage is required.")
        @DecimalMin(value = "0.0", message = "Percentage cannot be negative.")
        @Digits(integer = 3, fraction = 2)
        BigDecimal percentage,

        String notes,

        LoanStatus status,

        boolean negotiation
) {}
