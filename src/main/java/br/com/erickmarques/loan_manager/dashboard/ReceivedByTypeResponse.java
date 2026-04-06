package br.com.erickmarques.loan_manager.dashboard;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ReceivedByTypeResponse(
        BigDecimal totalInterest,
        BigDecimal totalFinished,
        BigDecimal totalAgreement
) {}