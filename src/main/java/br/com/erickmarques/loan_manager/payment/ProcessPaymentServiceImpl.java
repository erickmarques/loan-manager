package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.loan.Loan;
import br.com.erickmarques.loan_manager.loan.LoanRepository;
import br.com.erickmarques.loan_manager.loan.LoanStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    private final LoanRepository loanRepository;

    @Override
    public void process(Payment payment) {

        var loan = payment.getLoan();

        log.info("[PAYMENT] Processing payment of {} for Loan {}",
                payment.getAmount(), loan.getId());

        switch (payment.getType()) {
            case FINISHED -> closeLoan(loan, payment);
            case AGREEMENT -> applyNegotiationDiscount(loan, payment);
            case INTEREST -> postponeLoanDueDate(loan);
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported payment type: " + payment.getType()
            );
        }

        loanRepository.save(loan);
    }

    private void closeLoan(Loan loan, Payment payment) {
        log.info("[PAYMENT] Closing Loan {}. Payment {} >= total {}",
                loan.getId(), payment.getAmount(), loan.getTotalAmountToPay());

        loan.setStatus(LoanStatus.CLOSED);

        log.info("[PAYMENT] Loan {} successfully closed", loan.getId());
    }

    private void applyNegotiationDiscount(Loan loan, Payment payment) {

        log.info("[PAYMENT] Applying negotiation rules for Loan {}", loan.getId());

        var newAmount = loan.getAmount().subtract(payment.getAmount());
        var newTotal = loan.getTotalAmountToPay().subtract(payment.getAmount());

        loan.setAmount(newAmount.max(BigDecimal.ZERO));
        loan.setTotalAmountToPay(newTotal.max(BigDecimal.ZERO));

        if (payment.getNotes() == null || payment.getNotes().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "For payment agreements, please provide some notes!");
        }

        log.info("[PAYMENT] Negotiation updated Loan {}: newAmount = {}, newTotalToPay = {}",
                loan.getId(),
                loan.getAmount(),
                loan.getTotalAmountToPay()
        );
    }

    private void postponeLoanDueDate(Loan loan) {
        log.info("[PAYMENT] Postponing payment date for Loan {} (no negotiation)", loan.getId());

        loan.setPaymentDate(loan.getPaymentDate().plusMonths(1));

        log.info("[PAYMENT] Loan {} new paymentDate= {}", loan.getId(), loan.getPaymentDate());
    }
}
