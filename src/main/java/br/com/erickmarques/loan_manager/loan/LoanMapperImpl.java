package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.customer.Customer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
                .status(loan.getStatus().getLabel())
                .customer(createCustomerLoanResponse(loan))
                .createdAt(loan.getCreatedAt())
                .updatedAt(loan.getUpdatedAt())
                .build();
    }

    @Override
    public LoanResponse toResponse(Loan loan, BigDecimal totalReceived) {
        return LoanResponse.builder()
                .id(loan.getId())
                .loanDate(loan.getLoanDate())
                .paymentDate(loan.getPaymentDate())
                .amount(loan.getAmount())
                .percentage(loan.getPercentage())
                .totalAmountToPay(loan.getTotalAmountToPay())
                .negotiation(loan.isNegotiation())
                .notes(loan.getNotes())
                .status(loan.getStatus().getLabel())
                .customer(createCustomerLoanResponse(loan))
                .totalReceived(totalReceived)
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
    public Loan updateEntity(Loan existing, LoanRequestUpdate request) {
        return existing.toBuilder()
                .loanDate(request.loanDate())
                .paymentDate(request.paymentDate())
                .amount(request.amount())
                .percentage(request.percentage())
                .negotiation(request.negotiation())
                .totalAmountToPay(request.totalAmountToPay())
                .notes(request.notes())
                .status(request.status())
                .build();
    }

    private CustomerLoanResponse createCustomerLoanResponse(Loan loan){
        return CustomerLoanResponse.builder()
                .id(loan.getCustomer().getId())
                .name(loan.getCustomer().getName())
                .build();
    }
}
