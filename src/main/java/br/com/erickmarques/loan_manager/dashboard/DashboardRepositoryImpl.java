package br.com.erickmarques.loan_manager.dashboard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements DashboardRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DashboardSummaryResponse getSummary(LocalDate startDate) {
        return entityManager.createQuery("""
            SELECT new br.com.erickmarques.loan_manager.dashboard.DashboardSummaryResponse(
                COALESCE(SUM(l.amount), 0),
                (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate >= :startDate),
                ((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate >= :startDate)
                -
                (COALESCE(SUM(l.amount), 0))
                )
            )
            FROM Loan l
            WHERE l.loanDate >= :startDate
        """, DashboardSummaryResponse.class)
                .setParameter("startDate", startDate)
                .getSingleResult();
    }
}