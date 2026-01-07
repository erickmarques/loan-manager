package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.loan.CustomerLoanSummaryProjection;

public class CustomerLoanSummaryProjectionBuilder {

    public static CustomerLoanSummaryProjection create(){
        return new CustomerLoanSummaryProjection(1L, 2L);
    }
}
