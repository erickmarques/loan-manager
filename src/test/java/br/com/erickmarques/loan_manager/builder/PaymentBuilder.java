package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.payment.Payment;
import br.com.erickmarques.loan_manager.payment.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentBuilder {

    public static Payment createDefault() {
        var loan = LoanBuilder.createDefault();

        return Payment.builder()
                .id(UUID.fromString("7e9a7b5c-3f4e-4a7d-9e8f-123456789abc"))
                .paymentDate(LocalDate.now())
                .amount(BigDecimal.valueOf(150.75))
                .notes("Default payment for tests")
                .type(PaymentType.INTEREST)
                .loan(loan)
                .build();
    }
}