package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.builder.LoanBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentRequestBuilder;
import br.com.erickmarques.loan_manager.loan.LoanNotFoundException;
import br.com.erickmarques.loan_manager.loan.LoanRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl service;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private ProcessPaymentService processPaymentService;

    @Mock
    private LoanRepository loanRepository;

    @Nested
    class CreateTests {

        @Test
        void shouldCreatePaymentSuccessfully() {
            // Arrange
            var request = PaymentRequestBuilder.createDefault();
            var loan = LoanBuilder.createDefault();
            var payment = PaymentBuilder.createDefault();
            var response = PaymentResponse.builder().id(payment.getId()).build();

            when(loanRepository.findById(request.loanId())).thenReturn(Optional.of(loan));
            when(paymentMapper.toEntity(request, loan)).thenReturn(payment);
            when(paymentMapper.toResponse(payment)).thenReturn(response);

            // Act
            var result = service.create(request);

            // Assert
            assertNotNull(result);
            assertEquals(payment.getId(), result.id());
            verify(paymentRepository).save(payment);
        }

        @Test
        void shouldThrowWhenLoanNotFound() {
            // Arrange
            var request = PaymentRequestBuilder.createDefault();
            when(loanRepository.findById(request.loanId())).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(LoanNotFoundException.class, () -> service.create(request));
            verify(paymentRepository, never()).save(any());
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdatePaymentSuccessfully() {
            // Arrange
            var paymentId = UUID.randomUUID();
            var request = PaymentRequestBuilder.createDefault();
            var loan = LoanBuilder.createDefault();
            var existingPayment = PaymentBuilder.createDefault();
            var updatedPayment = PaymentBuilder.createDefault();
            var response = PaymentResponse.builder().id(updatedPayment.getId()).build();

            when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
            when(loanRepository.findById(request.loanId())).thenReturn(Optional.of(loan));
            when(paymentMapper.updateEntity(existingPayment, request, loan)).thenReturn(updatedPayment);
            when(paymentMapper.toResponse(updatedPayment)).thenReturn(response);

            // Act
            var result = service.update(paymentId, request);

            // Assert
            assertNotNull(result);
            assertEquals(updatedPayment.getId(), result.id());
            verify(paymentRepository).save(updatedPayment);
        }

        @Test
        void shouldThrowWhenPaymentNotFoundOnUpdate() {
            // Arrange
            var paymentId = UUID.randomUUID();
            var request = PaymentRequestBuilder.createDefault();
            when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(PaymentNotFoundException.class, () -> service.update(paymentId, request));
            verify(paymentRepository, never()).save(any());
        }

        @Test
        void shouldThrowWhenLoanNotFoundOnUpdate() {
            // Arrange
            var paymentId = UUID.randomUUID();
            var request = PaymentRequestBuilder.createDefault();
            var existingPayment = PaymentBuilder.createDefault();

            when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
            when(loanRepository.findById(request.loanId())).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(LoanNotFoundException.class, () -> service.update(paymentId, request));
            verify(paymentRepository, never()).save(any());
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        void shouldReturnPaymentSuccessfully() {
            // Arrange
            var paymentId = UUID.randomUUID();
            var payment = PaymentBuilder.createDefault();
            var response = PaymentResponse.builder().id(payment.getId()).build();

            when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
            when(paymentMapper.toResponse(payment)).thenReturn(response);

            // Act
            var result = service.findById(paymentId);

            // Assert
            assertNotNull(result);
            assertEquals(payment.getId(), result.id());
        }

        @Test
        void shouldThrowWhenPaymentNotFound() {
            // Arrange
            var paymentId = UUID.randomUUID();
            when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(PaymentNotFoundException.class, () -> service.findById(paymentId));
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldReturnAllPaymentsSuccessfully() {
            // Arrange
            var p1 = PaymentBuilder.createDefault();
            var p2 = PaymentBuilder.createDefault().toBuilder().id(UUID.randomUUID()).build();

            var r1 = PaymentResponse.builder().id(p1.getId()).build();
            var r2 = PaymentResponse.builder().id(p2.getId()).build();

            when(paymentRepository.findAllByOrderByPaymentDateAsc()).thenReturn(List.of(p1, p2));
            when(paymentMapper.toResponse(p1)).thenReturn(r1);
            when(paymentMapper.toResponse(p2)).thenReturn(r2);

            // Act
            var result = service.findAll();

            // Assert
            assertEquals(2, result.size());
            verify(paymentMapper).toResponse(p1);
            verify(paymentMapper).toResponse(p2);
        }
    }

    @Nested
    class FindAllByLoanIdTests {

        @Test
        void shouldReturnAllPaymentsForLoanSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            var p1 = PaymentBuilder.createDefault();
            var p2 = PaymentBuilder.createDefault().toBuilder().id(UUID.randomUUID()).build();

            var r1 = PaymentResponse.builder().id(p1.getId()).build();
            var r2 = PaymentResponse.builder().id(p2.getId()).build();

            when(paymentRepository.findAllByLoanIdOrderByPaymentDateAsc(loanId))
                    .thenReturn(List.of(p1, p2));
            when(paymentMapper.toResponse(p1)).thenReturn(r1);
            when(paymentMapper.toResponse(p2)).thenReturn(r2);

            // Act
            var result = service.findAllByLoanId(loanId);

            // Assert
            assertEquals(2, result.size());
            verify(paymentMapper).toResponse(p1);
            verify(paymentMapper).toResponse(p2);
        }
    }

    @Nested
    class DeleteTests {

        @Test
        void shouldDeletePaymentSuccessfully() {
            // Arrange
            var paymentId = UUID.randomUUID();
            when(paymentRepository.existsById(paymentId)).thenReturn(true);

            // Act
            service.deleteById(paymentId);

            // Assert
            verify(paymentRepository).deleteById(paymentId);
        }

        @Test
        void shouldThrowWhenPaymentDoesNotExist() {
            // Arrange
            var paymentId = UUID.randomUUID();
            when(paymentRepository.existsById(paymentId)).thenReturn(false);

            // Act + Assert
            assertThrows(PaymentNotFoundException.class, () -> service.deleteById(paymentId));
            verify(paymentRepository, never()).deleteById(any());
        }
    }
}
