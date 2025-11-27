package br.com.erickmarques.loan_manager.customer;

import br.com.erickmarques.loan_manager.builder.CustomerBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
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
class CustomerMapperImplTest {

    @InjectMocks
    private CustomerMapperImpl mapper;

    @Nested
    class ToEntityTests {

        @Test
        void shouldMapRequestToEntitySuccessfully() {
            // Arrange
            CustomerRequest request = CustomerRequestBuilder.createDefault();

            // Act
            Customer result = mapper.toEntity(request);

            // Assert
            assertNotNull(result);
            assertEquals(request.name(), result.getName());
            assertEquals(request.phone(), result.getPhone());
            assertEquals(request.notes(), result.getNotes());
        }

        @Test
        void shouldHandleNullFieldsGracefully() {
            // Arrange
            CustomerRequest request = CustomerRequest.builder().build();

            // Act
            Customer result = mapper.toEntity(request);

            // Assert
            assertNull(result.getName());
            assertNull(result.getPhone());
            assertNull(result.getNotes());
        }
    }

    @Nested
    class ToResponseTests {

        @Test
        void shouldMapCustomerToResponseSuccessfully() {
            // Arrange
            Customer customer = CustomerBuilder.createDefault();

            // Act
            CustomerResponse response = mapper.toResponse(customer);

            // Assert
            assertNotNull(response);
            assertEquals(customer.getId(), response.id());
            assertEquals(customer.getName(), response.name());
            assertEquals(customer.getPhone(), response.phone());
            assertEquals(customer.getNotes(), response.notes());
        }

        @Test
        void shouldThrowExceptionWhenCustomerIsNull() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> mapper.toResponse(null));
        }
    }

    @Nested
    class UpdateEntityTests {

        @Test
        void shouldUpdateEntitySuccessfully() {
            // Arrange
            Customer existing = CustomerBuilder.createDefault();
            CustomerRequest request = CustomerRequestBuilder.createDefault();

            // Act
            Customer updated = mapper.updateEntity(existing, request);

            // Assert
            assertNotNull(updated);
            assertEquals(request.name(), updated.getName());
            assertEquals(request.phone(), updated.getPhone());
            assertEquals(request.notes(), updated.getNotes());
        }

        @Test
        void shouldHandleNullFieldsWhenUpdating() {
            // Arrange
            Customer existing = CustomerBuilder.createDefault();
            CustomerRequest request = CustomerRequest.builder().build();

            // Act
            Customer updated = mapper.updateEntity(existing, request);

            // Assert
            assertNull(updated.getName());
            assertNull(updated.getPhone());
            assertNull(updated.getNotes());
        }
    }
}