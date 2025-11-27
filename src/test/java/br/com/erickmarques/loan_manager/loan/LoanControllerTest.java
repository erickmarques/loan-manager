package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.builder.LoanRequestBuilder;
import br.com.erickmarques.loan_manager.builder.LoanResponseBuilder;
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
class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @Nested
    class CreateLoanTests {

        @Test
        void shouldCreateLoanSuccessfully() {
            // Arrange
            var request = LoanRequestBuilder.createDefault();
            var response = LoanResponseBuilder.createDefault();

            when(loanService.create(request)).thenReturn(response);

            // Act
            ResponseEntity<LoanResponse> result = loanController.create(request);

            // Assert
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(loanService).create(request);
        }
    }

    @Nested
    class UpdateLoanTests {

        @Test
        void shouldUpdateLoanSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            var request = LoanRequestBuilder.createDefault();
            var response = LoanResponseBuilder.createDefault();

            when(loanService.update(loanId, request)).thenReturn(response);

            // Act
            ResponseEntity<LoanResponse> result = loanController.update(loanId, request);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(loanService).update(loanId, request);
        }
    }

    @Nested
    class FindLoanTests {

        @Test
        void shouldFindLoanByIdSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();
            var response = LoanResponseBuilder.createDefault();

            when(loanService.findById(loanId)).thenReturn(response);

            // Act
            ResponseEntity<LoanResponse> result = loanController.findById(loanId);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(loanService).findById(loanId);
        }

        @Test
        void shouldFindAllLoansSuccessfully() {
            // Arrange
            var loan1 = LoanResponseBuilder.createDefault();
            var loan2 = LoanResponseBuilder.createDefault();
            when(loanService.findAll()).thenReturn(List.of(loan1, loan2));

            // Act
            ResponseEntity<List<LoanResponse>> result = loanController.findAll();

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(2, result.getBody().size());
            verify(loanService).findAll();
        }

        @Test
        void shouldFindAllLoansByCustomerIdSuccessfully() {
            // Arrange
            var customerId = UUID.randomUUID();
            var loan1 = LoanResponseBuilder.createDefault();
            var loan2 = LoanResponseBuilder.createDefault();
            when(loanService.findAllByCustomerId(customerId)).thenReturn(List.of(loan1, loan2));

            // Act
            ResponseEntity<List<LoanResponse>> result = loanController.findAllByCustomerId(customerId);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(2, result.getBody().size());
            verify(loanService).findAllByCustomerId(customerId);
        }
    }

    @Nested
    class DeleteLoanTests {

        @Test
        void shouldDeleteLoanSuccessfully() {
            // Arrange
            var loanId = UUID.randomUUID();

            // Act
            ResponseEntity<Void> result = loanController.delete(loanId);

            // Assert
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(loanService).deleteById(loanId);
        }
    }
}