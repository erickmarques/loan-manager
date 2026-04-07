package br.com.erickmarques.loan_manager.upload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UploadRequest(
        @NotNull
        EntityType entityType,

        @NotNull
        UUID entityId,

        @NotBlank
        String fileName,

        @NotBlank
        String contentType
) { }