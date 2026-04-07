package br.com.erickmarques.loan_manager.upload;

import br.com.erickmarques.loan_manager.loan.LoanNotFoundException;
import br.com.erickmarques.loan_manager.loan.LoanRepository;
import br.com.erickmarques.loan_manager.payment.PaymentNotFoundException;
import br.com.erickmarques.loan_manager.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    private final S3Service s3Service;

    @Override
    public UploadUrlResponse generateUrl(UploadRequest request) {
        log.info("Generating url for {} and id {}.", request.entityType(), request.entityId());

        validateEntityExists(request);

        String key = buildKey(request.entityType().name(), request.entityId());

        String url = s3Service.generatePresignedUrl(key, request.contentType());

        return new UploadUrlResponse(url, key);
    }

    @Override
    public UploadUrlResponse  generateDownloadUrl(UUID key, EntityType type) {
        String buildKey = buildKey(type.name(), key);

        log.info("Generating URL for download | buildKey {}.", buildKey);

        String url = s3Service.generateDownloadUrl(buildKey);

        return new UploadUrlResponse(url, buildKey);
    }

    private void validateEntityExists(UploadRequest request) {
        switch (request.entityType()) {
            case LOAN -> loanRepository.findById(request.entityId())
                    .orElseThrow(() -> new LoanNotFoundException(request.entityId()));
            case PAYMENT -> paymentRepository.findById(request.entityId())
                    .orElseThrow(() -> new PaymentNotFoundException(request.entityId()));
        }

        log.info("Found {}.", request.entityType());
    }

    private String buildKey(String type, UUID id) {
        return String.format(
                "%s/%s/%s",
                type.toLowerCase(),
                id,
                id
        );
    }
}
