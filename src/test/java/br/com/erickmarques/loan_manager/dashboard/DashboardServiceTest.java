package br.com.erickmarques.loan_manager.dashboard;

import br.com.erickmarques.loan_manager.builder.ReceivedByTypeResponseBuilder;
import br.com.erickmarques.loan_manager.builder.SummaryResponseBuilder;
import br.com.erickmarques.loan_manager.builder.SummaryTotalResponseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardServiceImpl service;

    @Test
    void shouldReturnSummaryWhenDateIsProvided() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 4, 1);
        SummaryResponse expected = SummaryResponseBuilder.createDefault();

        when(dashboardRepository.getSummary(date))
                .thenReturn(expected);

        // Act
        SummaryResponse result = service.getSummaryForDate(date);

        // Assert
        assertEquals(expected, result);

        verify(dashboardRepository).getSummary(date);
        verifyNoMoreInteractions(dashboardRepository);
    }

    @Test
    void shouldReturnSummaryWithDefaultDateWhenDateIsNull() {
        // Arrange
        LocalDate expectedDate = LocalDate.now().minusDays(30);
        SummaryResponse expected = SummaryResponseBuilder.createDefault();

        when(dashboardRepository.getSummary(Mockito.any()))
                .thenReturn(expected);

        // Act
        SummaryResponse result = service.getSummaryForDate(null);

        // Assert
        assertEquals(expected, result);

        verify(dashboardRepository).getSummary(expectedDate);
        verifyNoMoreInteractions(dashboardRepository);
    }

    @Test
    void shouldReturnTotalSummary() {
        // Arrange
        SummaryTotalResponse expected = SummaryTotalResponseBuilder.createDefault();

        when(dashboardRepository.getTotalSummary())
                .thenReturn(expected);

        // Act
        SummaryTotalResponse result = service.getSummaryTotal();

        // Assert
        assertEquals(expected, result);

        verify(dashboardRepository).getTotalSummary();
        verifyNoMoreInteractions(dashboardRepository);
    }

    @Test
    void shouldReturnReceivedByTypeWhenDateIsProvided() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 4, 1);
        ReceivedByTypeResponse expected = ReceivedByTypeResponseBuilder.createDefault();

        when(dashboardRepository.getSummaryReceivedByType(date))
                .thenReturn(expected);

        // Act
        ReceivedByTypeResponse result =
                service.getSummaryReceivedByType(date);

        // Assert
        assertEquals(expected, result);

        verify(dashboardRepository).getSummaryReceivedByType(date);
        verifyNoMoreInteractions(dashboardRepository);
    }

    @Test
    void shouldReturnReceivedByTypeWithDefaultDateWhenDateIsNull() {
        // Arrange
        LocalDate expectedDate = LocalDate.now().minusDays(30);
        ReceivedByTypeResponse expected = ReceivedByTypeResponseBuilder.createDefault();

        when(dashboardRepository.getSummaryReceivedByType(Mockito.any()))
                .thenReturn(expected);

        // Act
        ReceivedByTypeResponse result =
                service.getSummaryReceivedByType(null);

        // Assert
        assertEquals(expected, result);

        verify(dashboardRepository).getSummaryReceivedByType(expectedDate);
        verifyNoMoreInteractions(dashboardRepository);
    }
}