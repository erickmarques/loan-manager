package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.dashboard.SummaryTotalResponse;

import java.math.BigDecimal;

public class SummaryTotalResponseBuilder {

    public static SummaryTotalResponse createDefault() {
        return SummaryTotalResponse.builder()
                .totalLoaned(new BigDecimal("1000.0"))
                .totalToReceive(new BigDecimal("1200.0"))
                .build();
    }
}