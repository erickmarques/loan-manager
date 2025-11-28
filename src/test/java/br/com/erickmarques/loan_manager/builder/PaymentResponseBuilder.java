package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.payment.PaymentResponse;
import br.com.erickmarques.loan_manager.payment.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentResponseBuilder {

    public static PaymentResponse createDefault() {
        return PaymentResponse.builder()
                .id(UUID.fromString("7e9a7b5c-3f4e-4a7d-9e8f-123456789abc"))
                .paymentDate(LocalDate.now())
                .amount(BigDecimal.valueOf(150.75))
                .notes("Default payment response")
                .type(PaymentType.INTEREST)
                .loanId(UUID.fromString("7e9a7b5c-3f4e-4a7d-9e8f-123456789abc"))
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
