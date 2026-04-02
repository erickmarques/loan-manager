package br.com.erickmarques.loan_manager.dashboard;


import java.time.LocalDate;

public interface DashboardService {
    SummaryResponse getSummaryForDate(LocalDate date);
    SummaryTotalResponse getSummaryTotal();
}
