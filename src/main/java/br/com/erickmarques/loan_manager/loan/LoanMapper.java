package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.customer.Customer;

public interface LoanMapper {

    LoanResponse toResponse(Loan loan);
    Loan toEntity(LoanRequest request, Customer customer);
    Loan updateEntity(Loan existing, LoanRequest request, Customer customer);
}
