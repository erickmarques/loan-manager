package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.builder.CustomerBuilder;
import br.com.erickmarques.loan_manager.builder.LoanBuilder;
import br.com.erickmarques.loan_manager.builder.LoanRequestBuilder;
import br.com.erickmarques.loan_manager.customer.CustomerNotFoundException;
import br.com.erickmarques.loan_manager.customer.CustomerRepository;
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
class LoanServiceImplTest {

    @InjectMocks
    private LoanServiceImpl service;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Nested
    class CreateTests {

        @Test
        void shouldCreateLoanSuccessfully() {
            // Arrange
            var request = LoanRequestBuilder.createWithCostumer(UUID.randomUUID());
            var customer = CustomerBuilder.createDefault();
            var loan = LoanBuilder.createDefault();
            var response = LoanResponse.builder().id(loan.getId()).build();

            when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
            when(loanMapper.toEntity(request, customer)).thenReturn(loan);
            when(loanMapper.toResponse(loan)).thenReturn(response);

            // Act
            var result = service.create(request);

            // Assert
            assertNotNull(result);
            assertEquals(loan.getId(), result.id());
            verify(loanRepository).save(loan);
        }

        @Test
        void shouldThrowWhenCustomerNotFound() {
            // Arrange
            var request = LoanRequestBuilder.createWithCostumer(UUID.randomUUID());
            when(customerRepository.findById(any())).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(CustomerNotFoundException.class, () -> service.create(request));
            verify(loanRepository, never()).save(any());
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateLoanSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            var request = LoanRequestBuilder.createDefault();
            var customer = CustomerBuilder.createDefault();
            var existingLoan = LoanBuilder.createDefault();
            var updatedLoan = LoanBuilder.createDefault();
            var response = LoanResponse.builder().id(updatedLoan.getId()).build();

            when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));
            when(loanMapper.updateEntity(existingLoan, request, customer)).thenReturn(updatedLoan);
            when(loanMapper.toResponse(updatedLoan)).thenReturn(response);

            // Act
            var result = service.update(loanId, request);

            // Assert
            assertNotNull(result);
            assertEquals(updatedLoan.getId(), result.id());
            verify(loanRepository).save(updatedLoan);
        }

        @Test
        void shouldThrowWhenLoanNotFoundOnUpdate() {
            // Arrange
            var loanId = UUID.randomUUID();
            var request = LoanRequestBuilder.createDefault();
            when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(LoanNotFoundException.class, () -> service.update(loanId, request));
            verify(loanRepository, never()).save(any());
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        void shouldReturnLoanByIdSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            var loan = LoanBuilder.createDefault();
            var response = LoanResponse.builder().id(loan.getId()).build();

            when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
            when(loanMapper.toResponse(loan)).thenReturn(response);

            // Act
            var result = service.findById(loanId);

            // Assert
            assertNotNull(result);
            assertEquals(loan.getId(), result.id());
        }

        @Test
        void shouldThrowWhenLoanNotFound() {
            // Arrange
            var loanId = UUID.randomUUID();
            when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(LoanNotFoundException.class, () -> service.findById(loanId));
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldReturnAllLoansSuccessfully() {
            // Arrange
            var loan1 = LoanBuilder.createDefault();
            var loan2 = LoanBuilder.createDefault().toBuilder().id(UUID.randomUUID()).build();
            var response1 = LoanResponse.builder().id(loan1.getId()).build();
            var response2 = LoanResponse.builder().id(loan2.getId()).build();

            when(loanRepository.findAllByOrderByPaymentDateAsc()).thenReturn(List.of(loan1, loan2));
            when(loanMapper.toResponse(loan1)).thenReturn(response1);
            when(loanMapper.toResponse(loan2)).thenReturn(response2);

            // Act
            var result = service.findAll();

            // Assert
            assertEquals(2, result.size());
            verify(loanMapper).toResponse(loan1);
            verify(loanMapper).toResponse(loan2);
        }
    }

    @Nested
    class FindAllByCustomerIdTests {

        @Test
        void shouldReturnAllLoansForCustomerSuccessfully() {
            // Arrange
            var customerId = UUID.randomUUID();
            var loan1 = LoanBuilder.createDefault();
            var loan2 = LoanBuilder.createDefault().toBuilder().id(UUID.randomUUID()).build();
            var response1 = LoanResponse.builder().id(loan1.getId()).build();
            var response2 = LoanResponse.builder().id(loan2.getId()).build();

            when(loanRepository.findAllByCustomerIdOrderByPaymentDateAsc(customerId))
                    .thenReturn(List.of(loan1, loan2));
            when(loanMapper.toResponse(loan1)).thenReturn(response1);
            when(loanMapper.toResponse(loan2)).thenReturn(response2);

            // Act
            var result = service.findAllByCustomerId(customerId);

            // Assert
            assertEquals(2, result.size());
            verify(loanMapper).toResponse(loan1);
            verify(loanMapper).toResponse(loan2);
        }
    }

    @Nested
    class DeleteTests {

        @Test
        void shouldDeleteLoanSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            when(loanRepository.existsById(loanId)).thenReturn(true);

            // Act
            service.deleteById(loanId);

            // Assert
            verify(loanRepository).deleteById(loanId);
        }

        @Test
        void shouldThrowWhenLoanDoesNotExistOnDelete() {
            // Arrange
            var loanId = UUID.randomUUID();
            when(loanRepository.existsById(loanId)).thenReturn(false);

            // Act + Assert
            assertThrows(LoanNotFoundException.class, () -> service.deleteById(loanId));
            verify(loanRepository, never()).deleteById(any());
        }
    }
}
