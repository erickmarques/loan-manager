package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.customer.Customer;

public interface LoanMapper {

    LoanResponse toResponse(Loan loan);
    Loan toEntity(LoanRequestCreate request, Customer customer);
    Loan updateEntity(Loan existing, LoanRequestUpdate request, Customer customer);
}
