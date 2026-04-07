package br.com.erickmarques.loan_manager.upload;

import java.util.UUID;

public interface FileService {
    FileResponse generateUrl(UploadRequest request);
    FileResponse generateDownloadUrl(UUID id, EntityType type);
}
