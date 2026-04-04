package br.com.erickmarques.loan_manager.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    INTEREST("Juros"),
    FINISHED("Finalizado"),
    AGREEMENT("Negociação");

    private final String label;

    public static PaymentType fromLabel(String label) {
        for (PaymentType type : PaymentType.values()) {
            if (type.getLabel().equalsIgnoreCase(label)) {
                return type;
            }
        }
        return PaymentType.INTEREST;
    }
}
