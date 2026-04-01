package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.loan.Loan;

public interface PaymentMapper {
    PaymentResponse toResponse(Payment payment);

    Payment toEntity(PaymentRequestCreate request, Loan loan);

    Payment updateEntity(Payment existing, PaymentRequestUpdate request);
}
