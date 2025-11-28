package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.payment.PaymentRequest;
import br.com.erickmarques.loan_manager.payment.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentRequestBuilder {

    public static PaymentRequest createDefault() {
        return PaymentRequest.builder()
                .paymentDate(LocalDate.now())
                .amount(BigDecimal.valueOf(150.75))
                .notes("Default payment request")
                .type(PaymentType.INTEREST)
                .loanId(UUID.fromString("7e9a7b5c-3f4e-4a7d-9e8f-123456789abc"))
                .build();
    }

    public static PaymentRequest createWithLoan(UUID loanId) {
        return PaymentRequest.builder()
                .paymentDate(LocalDate.now())
                .amount(BigDecimal.valueOf(150.75))
                .notes("Default payment request")
                .type(PaymentType.INTEREST)
                .loanId(loanId)
                .build();
    }
}
