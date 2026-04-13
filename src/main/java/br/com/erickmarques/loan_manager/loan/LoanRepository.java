package br.com.erickmarques.loan_manager.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findAllByCustomerIdAndStatusOrderByPaymentDateAsc(UUID customerId, LoanStatus status);
    List<Loan> findAllByStatusOrderByPaymentDateAsc(LoanStatus status);
    @Query("""
        SELECT new br.com.erickmarques.loan_manager.loan.LoanNotificationDTO(
            l.id,
            c.id,
            c.name,
            l.totalAmountToPay
        )
        FROM Loan l
        JOIN l.customer c
        WHERE l.paymentDate = :date
    """)
    List<LoanNotificationDTO> findAllByPaymentDate(LocalDate date);

    boolean existsByCustomerId(UUID customerId);

    @Query("""
        SELECT new br.com.erickmarques.loan_manager.loan.CustomerLoanSummaryProjection(
            COALESCE(SUM(CASE WHEN l.status = br.com.erickmarques.loan_manager.loan.LoanStatus.OPEN THEN 1 ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN l.status = br.com.erickmarques.loan_manager.loan.LoanStatus.CLOSED THEN 1 ELSE 0 END), 0)
        )
        FROM Loan l
        WHERE l.customer.id = :customerId
    """)
    CustomerLoanSummaryProjection countLoansByCustomer(@Param("customerId") UUID customerId);

}
