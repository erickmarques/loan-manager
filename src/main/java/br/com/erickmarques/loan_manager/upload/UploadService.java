package br.com.erickmarques.loan_manager.upload;

import java.util.UUID;

public interface UploadService {
    UploadUrlResponse generateUrl(UploadRequest request);
    UploadUrlResponse generateDownloadUrl(UUID key, EntityType type);
}
