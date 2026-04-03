package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.loan.CustomerLoanResponse;

import java.util.UUID;

public class LoanCustomerBuilder {

    public static CustomerLoanResponse createDefault(){
        return CustomerLoanResponse.builder()
                .id(UUID.randomUUID())
                .name("Teste Erick")
                .build();
    }
}
