package br.com.erickmarques.loan_manager.payment;

import java.util.UUID;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(UUID id) {
        super("Payment not found with ID: " + id);
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
