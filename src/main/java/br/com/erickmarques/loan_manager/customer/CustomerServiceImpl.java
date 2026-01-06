package br.com.erickmarques.loan_manager.customer;

import br.com.erickmarques.loan_manager.loan.LoanRepository;
import br.com.erickmarques.loan_manager.loan.LoanStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final LoanRepository loanRepository;

    @Override
    public CustomerResponse create(CustomerRequest request) {
        log.info("Creating customer.");

        var customer = customerMapper.toEntity(request);
        customerRepository.save(customer);

        log.info("Registered customer with ID {}.", customer.getId());

        var summary = loanRepository.countLoansByCustomer(customer.getId());

        return customerMapper.toResponse(customer, summary.openLoans(), summary.closedLoans());
    }

    @Override
    public CustomerResponse update(UUID id, CustomerRequest request) {
        log.info("Requesting customer update with ID {}.", id);

        var existing = findCustomerById(id);

        var updated = customerMapper.updateEntity(existing, request);
        customerRepository.save(updated);

        log.info("Updated customer with ID {}.", existing.getId());

        var summary = loanRepository.countLoansByCustomer(updated.getId());

        return customerMapper.toResponse(updated, summary.openLoans(), summary.closedLoans());
    }

    @Override
    public CustomerResponse findById(UUID id) {
        log.info("Finding customer by ID {}.", id);

        var customer = findCustomerById(id);

        var summary = loanRepository.countLoansByCustomer(customer.getId());

        return customerMapper.toResponse(customer, summary.openLoans(), summary.closedLoans());
    }

    @Override
    public List<CustomerResponse> findAll() {
        log.info("Finding all customers.");

        var customers = customerRepository.findAllByOrderByNameAsc();

        return customers.stream()
                .map(customer -> {
                    var summary = loanRepository.countLoansByCustomer(customer.getId());

                    return customerMapper.toResponse(
                            customer,
                            summary.openLoans(),
                            summary.closedLoans()
                    );
                })
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Requesting customer delete with ID {}.", id);

        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }

        customerRepository.deleteById(id);

        log.info("Customer was deleted.");
    }

    private Customer findCustomerById(UUID id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
