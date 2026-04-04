package br.com.erickmarques.loan_manager.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanStatus {
    OPEN("Aberto"),
    CLOSED("Finalizado");

    private final String label;

    public static LoanStatus fromLabel(String label) {
        for (LoanStatus type : LoanStatus.values()) {
            if (type.getLabel().equalsIgnoreCase(label)) {
                return type;
            }
        }
        return LoanStatus.OPEN;
    }
}
