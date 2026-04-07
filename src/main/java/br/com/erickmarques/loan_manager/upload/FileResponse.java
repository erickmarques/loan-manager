package br.com.erickmarques.loan_manager.upload;

import lombok.Builder;

@Builder
public record FileResponse(
        String url,
        String key
) {}