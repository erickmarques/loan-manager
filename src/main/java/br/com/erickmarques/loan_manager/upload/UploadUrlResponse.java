package br.com.erickmarques.loan_manager.upload;

import lombok.Builder;

@Builder
public record UploadUrlResponse(
        String url,
        String key
) {}