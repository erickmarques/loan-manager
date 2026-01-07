package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.loan.LoanRequestCreate;
import br.com.erickmarques.loan_manager.loan.LoanRequestUpdate;
import br.com.erickmarques.loan_manager.loan.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanRequestBuilder {

    public static LoanRequestUpdate createDefault(){
        return LoanRequestUpdate.builder()
                .loanDate(LocalDate.of(2025, 1, 10))
                .paymentDate(LocalDate.of(2025, 2, 10))
                .amount(new BigDecimal("1500.00"))
                .percentage(new BigDecimal("2.5"))
                .totalAmountToPay(new BigDecimal("1600.00"))
                .negotiation(false)
                .status(LoanStatus.OPEN)
                .build();

    }

    public static LoanRequestCreate createWithCostumer(UUID customerId){
        return LoanRequestCreate.builder()
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
