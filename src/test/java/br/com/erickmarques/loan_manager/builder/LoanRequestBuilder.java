package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.loan.LoanRequest;
import br.com.erickmarques.loan_manager.loan.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanRequestBuilder {

    public static LoanRequest createDefault(){
        return LoanRequest.builder()
                .loanDate(LocalDate.of(2025, 1, 10))
                .paymentDate(LocalDate.of(2025, 2, 10))
                .amount(new BigDecimal("1500.00"))
                .percentage(new BigDecimal("2.5"))
                .totalAmountToPay(new BigDecimal("1600.00"))
                .negotiation(false)
                .status(LoanStatus.OPEN)
                .customerId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .build();

    }

    public static LoanRequest createWithCostumer(UUID customerId){
        return LoanRequest.builder()
                .loanDate(LocalDate.of(2025, 1, 10))
                .paymentDate(LocalDate.of(2025, 2, 10))
                .amount(new BigDecimal("1500.00"))
                .percentage(new BigDecimal("2.5"))
                .totalAmountToPay(new BigDecimal("1600.00"))
                .negotiation(false)
                .customerId(customerId)
                .status(LoanStatus.OPEN)
                .build();

    }
}
