package br.com.erickmarques.loan_manager.payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponse create(PaymentRequestCreate request);

    PaymentResponse update(UUID id, PaymentRequestUpdate request);

    PaymentResponse findById(UUID id);

    List<PaymentResponse> findAll();

    List<PaymentResponse> findAllByLoanId(UUID loanId);

    void deleteById(UUID id);
}
