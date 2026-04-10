package br.com.erickmarques.loan_manager.dashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public SummaryResponse getSummaryForDate(LocalDate date) {
        LocalDate startDate = Optional.ofNullable(date)
                .orElse(LocalDate.now().minusDays(30));

        log.info("Get summary loans for date {}.", startDate);

        return dashboardRepository.getSummary(startDate);
    }

    @Override
    public SummaryTotalResponse getSummaryTotal() {
        log.info("Get summary total loans closed.");

        return dashboardRepository.getTotalSummary();
    }

    @Override
    public ReceivedByTypeResponse getSummaryReceivedByType(LocalDate date) {
        LocalDate startDate = Optional.ofNullable(date)
                .orElse(LocalDate.now().minusDays(30));

        log.info("Get summary received by type and date {}.", startDate);

        return dashboardRepository.getSummaryReceivedByType(startDate);
    }
}
