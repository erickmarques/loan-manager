package br.com.erickmarques.loan_manager.secutiry;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(UUID id, String name) {
}
