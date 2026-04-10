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
    public SummaryResponse getSummary(LocalDate startDate) {
        return entityManager.createQuery("""
            SELECT new br.com.erickmarques.loan_manager.dashboard.SummaryResponse(
                COALESCE(SUM(l.amount), 0),
                (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate >= :startDate),
                ((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate >= :startDate)
                -
                (COALESCE(SUM(l.amount), 0))
                ),
                (SELECT COUNT(lo)
                    FROM Loan lo
                 WHERE lo.paymentDate < CURRENT_DATE
                    AND lo.status = 'OPEN')
            )
            FROM Loan l
            WHERE l.loanDate >= :startDate
        """, SummaryResponse.class)
                .setParameter("startDate", startDate)
                .getSingleResult();
    }

    @Override
    public SummaryTotalResponse getTotalSummary() {
        return entityManager.createQuery("""
        SELECT new br.com.erickmarques.loan_manager.dashboard.SummaryTotalResponse(
                    COALESCE(SUM(l.amount), 0),
                    COALESCE(SUM(l.totalAmountToPay), 0)
                )
        FROM Loan l
        WHERE l.status = 'OPEN'
        """, SummaryTotalResponse.class).getSingleResult();
    }

    @Override
    public ReceivedByTypeResponse getSummaryReceivedByType(LocalDate localDate) {
        return entityManager.createQuery("""
        SELECT new br.com.erickmarques.loan_manager.dashboard.ReceivedByTypeResponse(
            COALESCE(SUM(CASE WHEN p.type = 'INTEREST' THEN p.amount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN p.type = 'FINISHED' THEN p.amount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN p.type = 'AGREEMENT' THEN p.amount ELSE 0 END), 0)
        )
        FROM Payment p
        WHERE p.paymentDate >= :startDate
    """, ReceivedByTypeResponse.class)
                .setParameter("startDate", localDate)
                .getSingleResult();
    }

}