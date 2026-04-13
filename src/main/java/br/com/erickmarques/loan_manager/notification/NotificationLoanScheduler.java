package br.com.erickmarques.loan_manager.notification;

import br.com.erickmarques.loan_manager.loan.LoanNotificationDTO;
import br.com.erickmarques.loan_manager.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationLoanScheduler {

    private final LoanRepository loanRepository;
    private final NotificationService notificationService;

    //@Scheduled(cron = "0 0 13 * * *", zone = "America/Sao_Paulo")
    @Scheduled(cron = "*/20 * * * * *")
    public void notifyLoansDueToday() {

        log.info("Starting verification of loans expiring today.");

        List<LoanNotificationDTO> loans = loanRepository.findAllByPaymentDate(LocalDate.now());

        log.info("{} loans found today", loans.size());

        loans.forEach(loan -> {
            try {

                String title = String.format(
                        "Empréstimo de %s vence hoje.",
                        loan.customerName()
                );

                String body = String.format(
                        "Empréstimo no valor %s, lembrar de mandar uma mensagem no whatsApp.",
                        loan.amount()
                );

                notificationService.sendToUser(title, body);

                log.info("Notification sent for use={}", loan.loanId());

            } catch (Exception e) {
                log.error("Error ending notification for loanId={}", loan.loanId(), e);
            }
        });

        log.info("Finishing notifications.");
    }
}