package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.dashboard.ReceivedByTypeResponse;

import java.math.BigDecimal;

public class ReceivedByTypeResponseBuilder {

    public static ReceivedByTypeResponse createDefault(){
        return ReceivedByTypeResponse.builder()
                .totalInterest(new BigDecimal("30.0"))
                .totalFinished(new BigDecimal("40.0"))
                .totalAgreement(new BigDecimal("50.0"))
                .build();
    }
}
