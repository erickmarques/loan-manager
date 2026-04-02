package br.com.erickmarques.loan_manager.dashboard;

import java.time.LocalDate;

public interface DashboardRepository {
    SummaryResponse getSummary(LocalDate localDate);
    SummaryTotalResponse getTotalSummary();
}
