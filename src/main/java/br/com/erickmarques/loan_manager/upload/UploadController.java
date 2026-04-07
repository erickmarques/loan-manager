package br.com.erickmarques.loan_manager.upload;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(
        name = "Uploads and Downloads API",
        description = "Operations related to upload and download file."
)
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/presigned-url")
    public UploadUrlResponse generateUrl(@RequestBody UploadRequest request) {
        return uploadService.generateUrl(request);
    }

    @GetMapping("/download-url")
    public UploadUrlResponse getFile(@RequestParam UUID key, @RequestParam EntityType type) {
        return uploadService.generateDownloadUrl(key, type);
    }
}
