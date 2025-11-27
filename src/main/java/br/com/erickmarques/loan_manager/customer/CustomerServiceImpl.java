package br.com.erickmarques.loan_manager.customer;

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

    @Override
    public CustomerResponse create(CustomerRequest request) {
        log.info("Creating customer.");

        var customer = customerMapper.toEntity(request);
        customerRepository.save(customer);

        log.info("Registered customer with ID {}.", customer.getId());

        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse update(UUID id, CustomerRequest request) {
        log.info("Requesting customer update with ID {}.", id);

        var existing = findCustomerById(id);

        var updated = customerMapper.updateEntity(existing, request);
        customerRepository.save(updated);

        log.info("Updated customer with ID {}.", existing.getId());

        return customerMapper.toResponse(updated);
    }

    @Override
    public CustomerResponse findById(UUID id) {
        log.info("Finding customer by ID {}.", id);

        var customer = findCustomerById(id);

        return customerMapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> findAll() {
        log.info("Finding all customers.");

        return customerRepository.findAllByOrderByNameAsc()
                .stream()
                .map(customerMapper::toResponse)
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
