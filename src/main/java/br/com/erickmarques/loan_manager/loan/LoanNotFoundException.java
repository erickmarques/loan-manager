package br.com.erickmarques.loan_manager.loan;

import java.util.UUID;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(UUID id) {
        super("Loan not found with ID: " + id);
    }

    public LoanNotFoundException(String message) {
        super(message);
    }
}