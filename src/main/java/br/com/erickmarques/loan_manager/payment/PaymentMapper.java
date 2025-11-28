package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.loan.Loan;

public interface PaymentMapper {
    PaymentResponse toResponse(Payment payment);

    Payment toEntity(PaymentRequest request, Loan loan);

    Payment updateEntity(Payment existing, PaymentRequest request, Loan loan);
}
