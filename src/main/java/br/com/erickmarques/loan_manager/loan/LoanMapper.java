package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.customer.Customer;

import java.math.BigDecimal;

public interface LoanMapper {

    LoanResponse toResponse(Loan loan);
    LoanResponse toResponse(Loan loan, BigDecimal totalReceived);
    Loan toEntity(LoanRequestCreate request, Customer customer);
    Loan updateEntity(Loan existing, LoanRequestUpdate request);
}
