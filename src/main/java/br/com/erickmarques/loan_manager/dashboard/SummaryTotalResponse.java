package br.com.erickmarques.loan_manager.dashboard;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SummaryTotalResponse(
        BigDecimal totalLoaned,
        BigDecimal totalToReceive
) {}