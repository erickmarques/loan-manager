package br.com.erickmarques.loan_manager.notification;

public interface NotificationService {
    void sendToUser(String title, String body);
}
