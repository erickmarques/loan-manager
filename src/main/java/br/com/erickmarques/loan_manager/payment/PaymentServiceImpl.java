package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.loan.Loan;
import br.com.erickmarques.loan_manager.loan.LoanNotFoundException;
import br.com.erickmarques.loan_manager.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final LoanRepository loanRepository;
    private final ProcessPaymentService processPaymentService;

    @Override
    public PaymentResponse create(PaymentRequest request) {
        var loan = findLoanById(request.loanId());
        var payment = paymentMapper.toEntity(request, loan);

        log.info("Receives payment of {} for loan ID {}.", request.amount(), loan.getId());

        paymentRepository.save(payment);

        processPaymentService.process(payment);

        log.info("Payment registered with ID {}.", payment.getId());

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse update(UUID id, PaymentRequest request) {
        log.info("Requesting payment update with ID {}.", id);

        var existing = findPaymentById(id);
        var loan = findLoanById(request.loanId());
        var updated = paymentMapper.updateEntity(existing, request, loan);

        paymentRepository.save(updated);

        log.info("Updated payment with ID {}.", id);

        return paymentMapper.toResponse(updated);
    }

    @Override
    public PaymentResponse findById(UUID id) {
        log.info("Finding payment by ID {}.", id);

        var payment = findPaymentById(id);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> findAll() {
        log.info("Finding all payments.");

        return paymentRepository.findAllByOrderByPaymentDateAsc()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> findAllByLoanId(UUID loanId) {
        log.info("Finding all payments for loanId {}.", loanId);

        return paymentRepository.findAllByLoanIdOrderByPaymentDateAsc(loanId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Requesting payment deletion with ID {}.", id);

        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException(id);
        }

        paymentRepository.deleteById(id);

        log.info("Payment deleted.");
    }

    private Payment findPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    private Loan findLoanById(UUID id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }
}
