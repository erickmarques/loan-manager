package br.com.erickmarques.loan_manager.device;

import jakarta.validation.constraints.NotEmpty;

public record DeviceTokenRequest(
    @NotEmpty
    String token
) {}