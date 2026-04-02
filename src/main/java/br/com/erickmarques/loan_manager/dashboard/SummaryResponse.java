package br.com.erickmarques.loan_manager.dashboard;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SummaryResponse(
        BigDecimal totalLoaned,
        BigDecimal totalReceived,
        BigDecimal netAmount
) {}