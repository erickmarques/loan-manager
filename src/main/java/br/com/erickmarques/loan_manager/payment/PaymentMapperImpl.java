package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .notes(payment.getNotes())
                .type(payment.getType())
                .loanId(payment.getLoan().getId())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    @Override
    public Payment toEntity(PaymentRequest request, Loan loan) {
        return Payment.builder()
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .notes(request.notes())
                .type(request.type())
                .loan(loan)
                .build();
    }

    @Override
    public Payment updateEntity(Payment existing, PaymentRequest request, Loan loan) {
        return existing.toBuilder()
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .notes(request.notes())
                .type(request.type())
                .loan(loan)
                .build();
    }
}
