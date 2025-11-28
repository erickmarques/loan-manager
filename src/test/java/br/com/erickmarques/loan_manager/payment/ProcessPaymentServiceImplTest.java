package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.builder.LoanBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentBuilder;
import br.com.erickmarques.loan_manager.loan.Loan;
import br.com.erickmarques.loan_manager.loan.LoanRepository;
import br.com.erickmarques.loan_manager.loan.LoanStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessPaymentServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private ProcessPaymentServiceImpl service;

    @Nested
    class FinishedPaymentTests {

        @Test
        void shouldCloseLoanWhenPaymentTypeIsFinished() {
            // Arrange
            Loan loan = LoanBuilder.createDefault();
            loan.setStatus(LoanStatus.OPEN);

            var payment = PaymentBuilder.createDefault();
            payment = payment.toBuilder()
                    .type(PaymentType.FINISHED)
                    .loan(loan)
                    .build();

            // Act
            service.process(payment);

            // Assert
            assertEquals(LoanStatus.CLOSED, loan.getStatus());
            verify(loanRepository).save(loan);
        }
    }

    @Nested
    class AgreementPaymentTests {

        @Test
        void shouldApplyNegotiationAndReduceAmounts() {
            // Arrange
            Loan loan = LoanBuilder.createDefault();
            loan.setAmount(new BigDecimal("1000.00"));
            loan.setTotalAmountToPay(new BigDecimal("1500.00"));

            var payment = PaymentBuilder.createDefault().toBuilder()
                    .type(PaymentType.AGREEMENT)
                    .amount(new BigDecimal("300.00"))
                    .notes("Special negotiation")
                    .loan(loan)
                    .build();

            // Act
            service.process(payment);

            // Assert
            assertEquals(new BigDecimal("700.00"), loan.getAmount());
            assertEquals(new BigDecimal("1200.00"), loan.getTotalAmountToPay());
            verify(loanRepository).save(loan);
        }

        @Test
        void shouldThrowWhenAgreementHasNoNotes() {
            // Arrange
            Loan loan = LoanBuilder.createDefault();

            var payment = PaymentBuilder.createDefault().toBuilder()
                    .type(PaymentType.AGREEMENT)
                    .notes("") // inválido
                    .loan(loan)
                    .build();

            // Act + Assert
            assertThrows(ResponseStatusException.class, () -> service.process(payment));
        }
    }

    @Nested
    class InterestPaymentTests {

        @Test
        void shouldPostponePaymentDateByOneMonth() {
            // Arrange
            Loan loan = LoanBuilder.createDefault();
            loan.setPaymentDate(LocalDate.of(2025, 2, 10));

            var payment = PaymentBuilder.createDefault().toBuilder()
                    .type(PaymentType.INTEREST)  // juros / atraso
                    .loan(loan)
                    .build();

            // Act
            service.process(payment);

            // Assert
            assertEquals(LocalDate.of(2025, 3, 10), loan.getPaymentDate());
            verify(loanRepository).save(loan);
        }
    }

    @Nested
    class GeneralFlowTests {

        @Test
        void shouldCallRepositorySaveAfterProcessing() {
            // Arrange
            Loan loan = LoanBuilder.createDefault();

            var payment = PaymentBuilder.createDefault().toBuilder()
                    .type(PaymentType.FINISHED)
                    .loan(loan)
                    .build();

            // Act
            service.process(payment);

            // Assert
            verify(loanRepository).save(loan);
        }
    }
}
