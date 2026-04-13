package br.com.erickmarques.loan_manager.notification;

import br.com.erickmarques.loan_manager.device.DeviceRepository;
import br.com.erickmarques.loan_manager.device.DeviceToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final DeviceRepository deviceRepository;

    @Override
    public void sendToUser(String title, String body) {

        List<DeviceToken> devices = deviceRepository.findAll();

        for (DeviceToken device : devices) {
            send(device.getToken(), title, body);
        }
    }

    private void send(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            log.info("Push sent successfully {}", response);

        } catch (Exception e) {
            log.error("Error send push", e);
        }
    }
}