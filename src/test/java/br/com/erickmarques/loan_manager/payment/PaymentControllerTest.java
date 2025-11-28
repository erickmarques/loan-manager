package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.builder.PaymentRequestBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentResponseBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Nested
    class CreatePaymentTests {

        @Test
        void shouldCreatePaymentSuccessfully() {
            // Arrange
            var request = PaymentRequestBuilder.createDefault();
            var response = PaymentResponseBuilder.createDefault();

            when(paymentService.create(request)).thenReturn(response);

            // Act
            ResponseEntity<PaymentResponse> result = paymentController.create(request);

            // Assert
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(paymentService).create(request);
        }
    }

    @Nested
    class UpdatePaymentTests {

        @Test
        void shouldUpdatePaymentSuccessfully() {
            // Arrange
            var paymentId = UUID.randomUUID();
            var request = PaymentRequestBuilder.createDefault();
            var response = PaymentResponseBuilder.createDefault();

            when(paymentService.update(paymentId, request)).thenReturn(response);

            // Act
            ResponseEntity<PaymentResponse> result = paymentController.update(paymentId, request);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(paymentService).update(paymentId, request);
        }
    }

    @Nested
    class FindPaymentTests {

        @Test
        void shouldFindPaymentByIdSuccessfully() {
            // Arrange
            var paymentId = UUID.randomUUID();
            var response = PaymentResponseBuilder.createDefault();

            when(paymentService.findById(paymentId)).thenReturn(response);

            // Act
            ResponseEntity<PaymentResponse> result = paymentController.findById(paymentId);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(paymentService).findById(paymentId);
        }

        @Test
        void shouldFindAllPaymentsSuccessfully() {
            // Arrange
            var p1 = PaymentResponseBuilder.createDefault();
            var p2 = PaymentResponseBuilder.createDefault();

            when(paymentService.findAll()).thenReturn(List.of(p1, p2));

            // Act
            ResponseEntity<List<PaymentResponse>> result = paymentController.findAll();

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(2, result.getBody().size());
            verify(paymentService).findAll();
        }

        @Test
        void shouldFindAllPaymentsByLoanIdSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            var p1 = PaymentResponseBuilder.createDefault();
            var p2 = PaymentResponseBuilder.createDefault();

            when(paymentService.findAllByLoanId(loanId)).thenReturn(List.of(p1, p2));

            // Act
            ResponseEntity<List<PaymentResponse>> result =
                    paymentController.findAllByLoanId(loanId);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(2, result.getBody().size());
            verify(paymentService).findAllByLoanId(loanId);
        }
    }

    @Nested
    class DeletePaymentTests {

        @Test
        void shouldDeletePaymentSuccessfully() {
            // Arrange
            var paymentId = UUID.randomUUID();

            // Act
            ResponseEntity<Void> result = paymentController.delete(paymentId);

            // Assert
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(paymentService).deleteById(paymentId);
        }
    }
}
