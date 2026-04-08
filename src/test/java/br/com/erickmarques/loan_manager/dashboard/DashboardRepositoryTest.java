package br.com.erickmarques.loan_manager.dashboard;

import br.com.erickmarques.loan_manager.builder.ReceivedByTypeResponseBuilder;
import br.com.erickmarques.loan_manager.builder.SummaryResponseBuilder;
import br.com.erickmarques.loan_manager.builder.SummaryTotalResponseBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<SummaryResponse> summaryQuery;

    @Mock
    private TypedQuery<SummaryTotalResponse> totalQuery;

    @Mock
    private TypedQuery<ReceivedByTypeResponse> receivedQuery;

    @InjectMocks
    private DashboardRepositoryImpl repository;

    @Test
    void shouldReturnSummaryWhenStartDateIsProvided() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(30);
        SummaryResponse expected = SummaryResponseBuilder.createDefault();

        when(entityManager.createQuery(anyString(), eq(SummaryResponse.class)))
                .thenReturn(summaryQuery);

        when(summaryQuery.setParameter("startDate", startDate))
                .thenReturn(summaryQuery);

        when(summaryQuery.getSingleResult())
                .thenReturn(expected);

        // Act
        SummaryResponse result = repository.getSummary(startDate);

        // Assert
        assertEquals(expected, result);

        verify(entityManager).createQuery(anyString(), eq(SummaryResponse.class));
        verify(summaryQuery).setParameter("startDate", startDate);
        verify(summaryQuery).getSingleResult();
        verifyNoMoreInteractions(entityManager, summaryQuery);
    }

    @Test
    void shouldReturnTotalSummary() {
        // Arrange
        SummaryTotalResponse expected = SummaryTotalResponseBuilder.createDefault();

        when(entityManager.createQuery(anyString(), eq(SummaryTotalResponse.class)))
                .thenReturn(totalQuery);

        when(totalQuery.getSingleResult())
                .thenReturn(expected);

        // Act
        SummaryTotalResponse result = repository.getTotalSummary();

        // Assert
        assertEquals(expected, result);

        verify(entityManager).createQuery(anyString(), eq(SummaryTotalResponse.class));
        verify(totalQuery).getSingleResult();
        verifyNoMoreInteractions(entityManager, totalQuery);
    }

    @Test
    void shouldReturnReceivedByTypeSummaryWhenStartDateIsProvided() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(10);
        ReceivedByTypeResponse expected = ReceivedByTypeResponseBuilder.createDefault();

        when(entityManager.createQuery(anyString(), eq(ReceivedByTypeResponse.class)))
                .thenReturn(receivedQuery);

        when(receivedQuery.setParameter("startDate", startDate))
                .thenReturn(receivedQuery);

        when(receivedQuery.getSingleResult())
                .thenReturn(expected);

        // Act
        ReceivedByTypeResponse result =
                repository.getSummaryReceivedByType(startDate);

        // Assert
        assertEquals(expected, result);

        verify(entityManager).createQuery(anyString(), eq(ReceivedByTypeResponse.class));
        verify(receivedQuery).setParameter("startDate", startDate);
        verify(receivedQuery).getSingleResult();
        verifyNoMoreInteractions(entityManager, receivedQuery);
    }
}
