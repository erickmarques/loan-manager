package br.com.erickmarques.loan_manager.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanStatus {
    OPEN("Aberto"),
    CLOSED("Finalizado");

    private final String label;
}
