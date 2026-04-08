package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.dashboard.SummaryResponse;

import java.math.BigDecimal;

public class SummaryResponseBuilder {

    public static SummaryResponse createDefault() {
        return SummaryResponse.builder()
                .totalLoaned(new BigDecimal("100.0"))
                .totalReceived(new BigDecimal("50.0"))
                .netAmount(new BigDecimal("-50.0"))
                .overdueLoans(2L)
                .build();
    }
}