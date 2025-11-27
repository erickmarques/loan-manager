package br.com.erickmarques.loan_manager.customer;

import br.com.erickmarques.loan_manager.builder.CustomerBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerResponseBuilder;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl service;

    @Nested
    class CreateTests {

        @Test
        void shouldCreateCustomerSuccessfully() {
            // Arrange
            var request = CustomerRequestBuilder.createDefault();
            var customer = CustomerBuilder.createDefault();
            var response = CustomerResponseBuilder.createDefault();

            when(customerMapper.toEntity(request)).thenReturn(customer);
            when(customerMapper.toResponse(customer)).thenReturn(response);

            // Act
            var result = service.create(request);

            // Assert
            assertNotNull(result);
            verify(customerRepository).save(customer);
            verify(customerMapper).toEntity(request);
            verify(customerMapper).toResponse(customer);
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateCustomerSuccessfully() {
            // Arrange
            UUID id = UUID.randomUUID();
            var request = CustomerRequestBuilder.createDefault();
            var existing = CustomerBuilder.createDefault();
            var updated = CustomerBuilder.createDefault();
            var response = CustomerResponseBuilder.createDefault();

            when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
            when(customerMapper.updateEntity(existing, request)).thenReturn(updated);
            when(customerMapper.toResponse(updated)).thenReturn(response);

            // Act
            var result = service.update(id, request);

            // Assert
            assertNotNull(result);
            verify(customerRepository).save(updated);
            verify(customerMapper).updateEntity(existing, request);
            verify(customerMapper).toResponse(updated);
        }

        @Test
        void shouldThrowWhenCustomerNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();
            var request = CustomerRequestBuilder.createDefault();

            when(customerRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> service.update(id, request));
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        void shouldFindCustomerByIdSuccessfully() {
            // Arrange
            UUID id = UUID.randomUUID();
            var customer = CustomerBuilder.createDefault();
            var response = CustomerResponseBuilder.createDefault();

            when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
            when(customerMapper.toResponse(customer)).thenReturn(response);

            // Act
            var result = service.findById(id);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);
        }

        @Test
        void shouldThrowWhenCustomerNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();
            when(customerRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> service.findById(id));
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldReturnAllCustomersSuccessfully() {
            // Arrange
            var customer1 = CustomerBuilder.createDefault();
            var customer2 = CustomerBuilder.createDefault().toBuilder()
                    .id(UUID.randomUUID())
                    .build();

            var response1 = CustomerResponseBuilder.createDefault();
            var response2 = CustomerResponseBuilder.createDefault();

            when(customerRepository.findAllByOrderByNameAsc()).thenReturn(List.of(customer1, customer2));
            when(customerMapper.toResponse(customer1)).thenReturn(response1);
            when(customerMapper.toResponse(customer2)).thenReturn(response2);

            // Act
            var result = service.findAll();

            // Assert
            assertEquals(2, result.size());
            verify(customerMapper).toResponse(customer1);
            verify(customerMapper).toResponse(customer2);
        }
    }

    @Nested
    class DeleteByIdTests {

        @Test
        void shouldDeleteCustomerSuccessfully() {
            // Arrange
            UUID id = UUID.randomUUID();
            when(customerRepository.existsById(id)).thenReturn(true);

            // Act
            service.deleteById(id);

            // Assert
            verify(customerRepository).deleteById(id);
        }

        @Test
        void shouldThrowWhenDeletingNonexistentCustomer() {
            // Arrange
            UUID id = UUID.randomUUID();
            when(customerRepository.existsById(id)).thenReturn(false);

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> service.deleteById(id));
        }
    }
}