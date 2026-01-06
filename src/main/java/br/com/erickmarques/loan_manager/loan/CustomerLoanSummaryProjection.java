package br.com.erickmarques.loan_manager.loan;


public record CustomerLoanSummaryProjection(
        Long openLoans,
        Long closedLoans
) {}

