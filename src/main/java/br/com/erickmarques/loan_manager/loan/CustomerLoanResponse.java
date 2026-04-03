package br.com.erickmarques.loan_manager.loan;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerLoanResponse(UUID id, String name) {
}
