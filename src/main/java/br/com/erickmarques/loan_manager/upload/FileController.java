package br.com.erickmarques.loan_manager.upload;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
public class FileController {

    private final FileService fileService;

    @PostMapping("/presigned-url")
    public FileResponse generateUrl(@RequestBody @Validated UploadRequest request) {
        return fileService.generateUrl(request);
    }

    @GetMapping("/download-url")
    public FileResponse getFile(@RequestParam UUID id, @RequestParam EntityType type) {
        return fileService.generateDownloadUrl(id, type);
    }
}
