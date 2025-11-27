package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.customer.Customer;
import br.com.erickmarques.loan_manager.loan.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoanBuilder {

    public static Loan createDefault() {
        Customer defaultCustomer = CustomerBuilder.createDefault();

        return Loan.builder()
                .id(UUID.fromString("7e9a7b5c-3f4e-4a7d-9e8f-123456789abc"))
                .loanDate(LocalDate.of(2025, 1, 5))
                .paymentDate(LocalDate.of(2025, 2, 5))
                .amount(new BigDecimal("200.00"))
                .percentage(new BigDecimal("5.0"))
                .totalAmountToPay(new BigDecimal("210.00"))
                .negotiation(false)
                .customer(defaultCustomer)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
