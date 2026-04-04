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
                .type(payment.getType().getLabel())
                .loanId(payment.getLoan().getId())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    @Override
    public Payment toEntity(PaymentRequestCreate request, Loan loan) {
        return Payment.builder()
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .notes(request.notes())
                .type(PaymentType.fromLabel(request.type()))
                .loan(loan)
                .build();
    }

    @Override
    public Payment updateEntity(Payment existing, PaymentRequestUpdate request) {
        return existing.toBuilder()
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .notes(request.notes())
                .type(PaymentType.fromLabel(request.type()))
                .build();
    }
}
