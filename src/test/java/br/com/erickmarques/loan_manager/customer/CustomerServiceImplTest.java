package br.com.erickmarques.loan_manager.customer;

import br.com.erickmarques.loan_manager.builder.CustomerBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerLoanSummaryProjectionBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
import br.com.erickmarques.loan_manager.builder.CustomerResponseBuilder;
import br.com.erickmarques.loan_manager.loan.CustomerLoanSummaryProjection;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private LoanRepository loanRepository;

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
            var quantityOpenLoans = 1L;
            var quantityClosedLoans = 2L;

            when(customerMapper.toEntity(request)).thenReturn(customer);
            when(customerMapper.toResponse(customer, quantityOpenLoans, quantityClosedLoans)).thenReturn(response);
            when(loanRepository.countLoansByCustomer(any())).thenReturn(CustomerLoanSummaryProjectionBuilder.create());

            // Act
            var result = service.create(request);

            // Assert
            assertNotNull(result);
            verify(customerRepository).save(customer);
            verify(customerMapper).toEntity(request);
            verify(customerMapper).toResponse(customer, quantityOpenLoans, quantityClosedLoans);
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
            var quantityOpenLoans = 1L;
            var quantityClosedLoans = 2L;

            when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
            when(customerMapper.updateEntity(existing, request)).thenReturn(updated);
            when(customerMapper.toResponse(updated, quantityOpenLoans, quantityClosedLoans)).thenReturn(response);
            when(loanRepository.countLoansByCustomer(any())).thenReturn(CustomerLoanSummaryProjectionBuilder.create());

            // Act
            var result = service.update(id, request);

            // Assert
            assertNotNull(result);
            verify(customerRepository).save(updated);
            verify(customerMapper).updateEntity(existing, request);
            verify(customerMapper).toResponse(updated, quantityOpenLoans, quantityClosedLoans);
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
            var quantityOpenLoans = 1L;
            var quantityClosedLoans = 2L;

            when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
            when(customerMapper.toResponse(customer, quantityOpenLoans, quantityClosedLoans)).thenReturn(response);
            when(loanRepository.countLoansByCustomer(any())).thenReturn(CustomerLoanSummaryProjectionBuilder.create());

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
            var quantityOpenLoans = 1L;
            var quantityClosedLoans = 2L;

            when(customerRepository.findAllByOrderByNameAsc()).thenReturn(List.of(customer1, customer2));
            when(customerMapper.toResponse(customer1, quantityOpenLoans, quantityClosedLoans)).thenReturn(response1);
            when(customerMapper.toResponse(customer2, quantityOpenLoans, quantityClosedLoans)).thenReturn(response2);
            when(loanRepository.countLoansByCustomer(any())).thenReturn(CustomerLoanSummaryProjectionBuilder.create());

            // Act
            var result = service.findAll();

            // Assert
            assertEquals(2, result.size());
            verify(customerMapper).toResponse(customer1, quantityOpenLoans, quantityClosedLoans);
            verify(customerMapper).toResponse(customer2, quantityOpenLoans, quantityClosedLoans);
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