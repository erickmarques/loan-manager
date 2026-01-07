package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.loan.LoanResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoanResponseBuilder {

    public static LoanResponse createDefault() {
        return LoanResponse.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .loanDate(LocalDate.of(2025, 1, 10))
                .paymentDate(LocalDate.of(2025, 2, 10))
                .amount(new BigDecimal("1500.00"))
                .percentage(new BigDecimal("2.5"))
                .totalAmountToPay(new BigDecimal("1600.00"))
                .negotiation(true)
                .customerName("Teste Erick")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
