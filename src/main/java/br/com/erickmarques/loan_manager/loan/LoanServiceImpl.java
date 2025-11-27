package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.customer.Customer;
import br.com.erickmarques.loan_manager.customer.CustomerNotFoundException;
import br.com.erickmarques.loan_manager.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final CustomerRepository customerRepository;

    @Override
    public LoanResponse create(LoanRequest request) {
        var customer = findCustomerById(request.customerId());
        var loan = loanMapper.toEntity(request, customer);

        log.info("Creating loan of {} for customer ID {}.", request.totalAmountToPay(), customer.getId());

        loanRepository.save(loan);

        log.info("Loan registered with ID {}.", loan.getId());

        return loanMapper.toResponse(loan);
    }

    @Override
    public LoanResponse update(UUID id, LoanRequest request) {
        log.info("Requesting loan update with ID {}.", id);

        var existing = findLoanById(id);
        var customer = findCustomerById(request.customerId());
        var updated = loanMapper.updateEntity(existing, request, customer);

        loanRepository.save(updated);

        log.info("Updated loan with ID {}.", id);

        return loanMapper.toResponse(updated);
    }

    @Override
    public LoanResponse findById(UUID id) {
        log.info("Finding loan by ID {}.", id);

        var loan = findLoanById(id);

        return loanMapper.toResponse(loan);
    }

    @Override
    public List<LoanResponse> findAll() {
        log.info("Finding all loans.");

        return loanRepository.findAllByOrderByPaymentDateAsc()
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    @Override
    public List<LoanResponse> findAllByCustomerId(UUID customerId) {
        log.info("Finding all loans for customerId {}.", customerId);

        return loanRepository.findAllByCustomerIdOrderByPaymentDateAsc(customerId)
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }


    @Override
    public void deleteById(UUID id) {
        log.info("Requesting loan deletion with ID {}.", id);

        if (!loanRepository.existsById(id)) {
            throw new LoanNotFoundException(id);
        }

        loanRepository.deleteById(id);

        log.info("Loan deleted.");
    }

    private Loan findLoanById(UUID id){
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    private Customer findCustomerById(UUID id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
