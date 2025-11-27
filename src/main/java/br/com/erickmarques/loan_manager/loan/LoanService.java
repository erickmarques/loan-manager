package br.com.erickmarques.loan_manager.loan;

import java.util.List;
import java.util.UUID;

public interface LoanService {

    LoanResponse create(LoanRequest request);

    LoanResponse update(UUID id, LoanRequest request);

    LoanResponse findById(UUID id);

    List<LoanResponse> findAll();

    List<LoanResponse> findAllByCustomerId(UUID customerId);

    void deleteById(UUID id);
}
