package br.com.erickmarques.loan_manager.dashboard;

import java.time.LocalDate;

public interface DashboardRepository {
    public DashboardSummaryResponse getSummary(LocalDate localDate);
}
