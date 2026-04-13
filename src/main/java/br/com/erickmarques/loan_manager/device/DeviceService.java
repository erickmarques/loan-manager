package br.com.erickmarques.loan_manager.device;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository repository;

    public void save(DeviceTokenRequest request, UUID userId) {
        log.info("Saving device. userId={}, token={}", userId, request.token());


        repository.findByToken(request.token())
                .ifPresentOrElse(existingDevice -> {

                    if (!existingDevice.getUserId().equals(userId)) {
                        existingDevice.setUserId(userId);
                        repository.save(existingDevice);

                        log.info("Device updated for new userId={}", userId);
                    } else {
                        log.info("Existing device, no modifications needed.");
                    }

                }, () -> {

                    repository.save(
                            DeviceToken.builder()
                                    .userId(userId)
                                    .token(request.token())
                                    .build()
                    );

                    log.info("New device created.");
                });
    }
}