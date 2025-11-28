package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.builder.CustomerBuilder;
import br.com.erickmarques.loan_manager.builder.LoanBuilder;
import br.com.erickmarques.loan_manager.builder.LoanRequestBuilder;
import br.com.erickmarques.loan_manager.customer.Customer;
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
class LoanMapperImplTest {

    @InjectMocks
    private LoanMapperImpl mapper;

    @Nested
    class ToEntityTests {

        @Test
        void shouldMapRequestToEntitySuccessfully() {
            // Arrange
            var request = LoanRequestBuilder.createDefault();
            var customer = CustomerBuilder.createDefault();

            // Act
            var result = mapper.toEntity(request, customer);

            // Assert
            assertNotNull(result);
            assertEquals(request.loanDate(), result.getLoanDate());
            assertEquals(request.amount(), result.getAmount());
            assertEquals(request.notes(), result.getNotes());
            assertEquals(request.status(), result.getStatus());
            assertEquals(customer, result.getCustomer());
        }

        @Test
        void shouldFailWhenRequestHasNullRequiredFields() {
            // Arrange
            var request = LoanRequest.builder().build();
            var customer = Customer.builder().build();

            // Act
            var result = mapper.toEntity(request, customer);

            // Assert
            assertNull(result.getLoanDate());
            assertNull(result.getAmount());
        }
    }

    @Nested
    class ToResponseTests {

        @Test
        void shouldMapLoanToResponseSuccessfully() {
            // Arrange
            var loan = LoanBuilder.createDefault();

            // Act
            var response = mapper.toResponse(loan);

            // Assert
            assertNotNull(response);
            assertEquals(loan.getId(), response.id());
            assertEquals(loan.getCustomer().getId(), response.customerId());
        }

        @Test
        void shouldHandleNullCustomerGracefully() {
            // Arrange
            var loan = Loan.builder().build();

            // Act + Assert
            assertThrows(NullPointerException.class, () -> mapper.toResponse(loan));
        }
    }

    @Nested
    class UpdateEntityTests {

        @Test
        void shouldUpdateEntitySuccessfully() {
            // Arrange
            var existing = Loan.builder().build();
            var request = LoanRequestBuilder.createDefault();
            var customer = CustomerBuilder.createDefault();

            // Act
            var updated = mapper.updateEntity(existing, request, customer);

            // Assert
            assertNotNull(updated);
            assertEquals(request.loanDate(), updated.getLoanDate());
            assertEquals(customer, updated.getCustomer());
        }

        @Test
        void shouldNotBreakWhenUpdatingUsingNullFields() {
            // Arrange
            Loan existing = Loan.builder().build();
            var request = LoanRequest.builder().build();
            var customer = Customer.builder().build();

            // Act
            var updated = mapper.updateEntity(existing, request, customer);

            // Assert
            assertNull(updated.getLoanDate());
            assertNull(updated.getAmount());
        }
    }
}