package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .loanDate(loan.getLoanDate())
                .paymentDate(loan.getPaymentDate())
                .amount(loan.getAmount())
                .percentage(loan.getPercentage())
                .totalAmountToPay(loan.getTotalAmountToPay())
                .negotiation(loan.isNegotiation())
                .notes(loan.getNotes())
                .status(loan.getStatus())
                .customerName(loan.getCustomer().getName())
                .createdAt(loan.getCreatedAt())
                .updatedAt(loan.getUpdatedAt())
                .build();
    }

    @Override
    public Loan toEntity(LoanRequestCreate request, Customer customer) {
        return Loan.builder()
                .loanDate(request.loanDate())
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .percentage(request.percentage())
                .negotiation(request.negotiation())
                .customer(customer)
                .totalAmountToPay(request.totalAmountToPay())
                .notes(request.notes())
                .status(request.status())
                .build();
    }

    @Override
    public Loan updateEntity(Loan existing, LoanRequestUpdate request, Customer customer) {
        return existing.toBuilder()
                .loanDate(request.loanDate())
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .percentage(request.percentage())
                .negotiation(request.negotiation())
                .totalAmountToPay(request.totalAmountToPay())
                .customer(customer)
                .build();
    }
}
