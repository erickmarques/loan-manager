package br.com.erickmarques.loan_manager.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findAllByCustomerIdOrderByPaymentDateAsc(UUID customerId);
    List<Loan> findAllByOrderByPaymentDateAsc();



    @Query("""
        SELECT new br.com.erickmarques.loan_manager.loan.CustomerLoanSummaryProjection(
            SUM(CASE WHEN l.status = br.com.erickmarques.loan_manager.loan.LoanStatus.OPEN THEN 1 ELSE 0 END),
            SUM(CASE WHEN l.status = br.com.erickmarques.loan_manager.loan.LoanStatus.CLOSED THEN 1 ELSE 0 END)
        )
        FROM Loan l
        WHERE l.customer.id = :customerId
    """)
    CustomerLoanSummaryProjection countLoansByCustomer(@Param("customerId") UUID customerId);

}
