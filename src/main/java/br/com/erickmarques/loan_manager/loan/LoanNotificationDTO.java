package br.com.erickmarques.loan_manager.loan;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanNotificationDTO(
        UUID loanId,
        UUID customerId,
        String customerName,
        BigDecimal amount
) {}
