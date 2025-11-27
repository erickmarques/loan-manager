package br.com.erickmarques.loan_manager.customer;

import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerResponseBuilder;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService customerService;

    @Nested
    class CreateTests {

        @Test
        void shouldCreateCustomerSuccessfully() {
            // Arrange
            var request = CustomerRequestBuilder.createDefault();
            var response = CustomerResponseBuilder.createDefault();

            when(customerService.create(request)).thenReturn(response);

            // Act
            ResponseEntity<CustomerResponse> result = controller.create(request);

            // Assert
            assertNotNull(result);
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(customerService).create(request);
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateCustomerSuccessfully() {
            // Arrange
            var id = UUID.randomUUID();
            var request = CustomerRequestBuilder.createDefault();
            var response = CustomerResponseBuilder.createDefault();

            when(customerService.update(id, request)).thenReturn(response);

            // Act
            ResponseEntity<CustomerResponse> result = controller.update(id, request);

            // Assert
            assertNotNull(result);
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(customerService).update(id, request);
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        void shouldReturnCustomerByIdSuccessfully() {
            // Arrange
            var id = UUID.randomUUID();
            var response = CustomerResponseBuilder.createDefault();

            when(customerService.findById(id)).thenReturn(response);

            // Act
            ResponseEntity<CustomerResponse> result = controller.findById(id);

            // Assert
            assertNotNull(result);
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(customerService).findById(id);
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldReturnAllCustomersSuccessfully() {
            // Arrange
            var customer1 = CustomerResponseBuilder.createDefault();
            var customer2 = CustomerResponseBuilder.createDefault();
            var allCustomers = List.of(customer1, customer2);

            when(customerService.findAll()).thenReturn(allCustomers);

            // Act
            ResponseEntity<List<CustomerResponse>> result = controller.findAll();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.getBody().size());
            assertEquals(allCustomers, result.getBody());
            verify(customerService).findAll();
        }
    }

    @Nested
    class DeleteTests {

        @Test
        void shouldDeleteCustomerSuccessfully() {
            // Arrange
            var id = UUID.randomUUID();

            doNothing().when(customerService).deleteById(id);

            // Act
            var result = controller.delete(id);

            // Assert
            assertNotNull(result);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(customerService).deleteById(id);
        }
    }
}
