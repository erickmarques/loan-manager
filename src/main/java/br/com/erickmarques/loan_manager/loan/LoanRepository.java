package br.com.erickmarques.loan_manager.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findAllByCustomerIdOrderByPaymentDateAsc(UUID customerId);
    List<Loan> findAllByOrderByPaymentDateAsc();
}
