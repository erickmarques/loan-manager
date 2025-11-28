package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.builder.LoanBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentRequestBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PaymentMapperImplTest {

    @InjectMocks
    private PaymentMapperImpl mapper;

    @Nested
    class ToEntityTests {

        @Test
        void shouldMapRequestToEntitySuccessfully() {
            // Arrange
            var loan = LoanBuilder.createDefault();
            var request = PaymentRequestBuilder.createDefault();

            // Act
            var result = mapper.toEntity(request, loan);

            // Assert
            assertNotNull(result);
            assertEquals(request.paymentDate(), result.getPaymentDate());
            assertEquals(request.amount(), result.getAmount());
            assertEquals(request.notes(), result.getNotes());
            assertEquals(request.type(), result.getType());
            assertEquals(loan, result.getLoan());
        }

        @Test
        void shouldHandleNullFieldsGracefully() {
            // Arrange
            var loan = LoanBuilder.createDefault();
            var request = PaymentRequest.builder().build();

            // Act
            var result = mapper.toEntity(request, loan);

            // Assert
            assertNull(result.getPaymentDate());
            assertNull(result.getAmount());
            assertNull(result.getNotes());
            assertNull(result.getType());
            assertEquals(loan, result.getLoan());
        }
    }

    @Nested
    class ToResponseTests {

        @Test
        void shouldMapEntityToResponseSuccessfully() {
            // Arrange
            var payment = PaymentBuilder.createDefault();

            // Act
            PaymentResponse response = mapper.toResponse(payment);

            // Assert
            assertNotNull(response);
            assertEquals(payment.getId(), response.id());
            assertEquals(payment.getPaymentDate(), response.paymentDate());
            assertEquals(payment.getAmount(), response.amount());
            assertEquals(payment.getNotes(), response.notes());
            assertEquals(payment.getType(), response.type());
            assertEquals(payment.getLoan().getId(), response.loanId());
            assertEquals(payment.getCreatedAt(), response.createdAt());
            assertEquals(payment.getUpdatedAt(), response.updatedAt());
        }

        @Test
        void shouldThrowExceptionWhenPaymentIsNull() {
            assertThrows(NullPointerException.class, () -> mapper.toResponse(null));
        }
    }

    @Nested
    class UpdateEntityTests {

        @Test
        void shouldUpdateEntitySuccessfully() {
            // Arrange
            var loan = LoanBuilder.createDefault();
            var existing = PaymentBuilder.createDefault();
            var request = PaymentRequestBuilder.createDefault();

            // Act
            var updated = mapper.updateEntity(existing, request, loan);

            // Assert
            assertNotNull(updated);
            assertEquals(request.paymentDate(), updated.getPaymentDate());
            assertEquals(request.amount(), updated.getAmount());
            assertEquals(request.notes(), updated.getNotes());
            assertEquals(request.type(), updated.getType());
            assertEquals(loan, updated.getLoan());
        }

        @Test
        void shouldHandleNullFieldsWhenUpdating() {
            // Arrange
            var loan = LoanBuilder.createDefault();
            var existing = PaymentBuilder.createDefault();
            var request = PaymentRequest.builder().build();

            // Act
            var updated = mapper.updateEntity(existing, request, loan);

            // Assert
            assertNull(updated.getPaymentDate());
            assertNull(updated.getAmount());
            assertNull(updated.getNotes());
            assertNull(updated.getType());
            assertEquals(loan, updated.getLoan());
        }
    }
}
