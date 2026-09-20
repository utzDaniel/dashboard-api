package br.com.dashboard.modules.payroll.dto;

import lombok.Getter;

@Getter
public enum PayrollEvent {

    SALARIO(1, "Salário"),
    FLASH(8, "Flash"),
    ALELO(9, "Alelo"),
    PLR(101, "PLR"),
    SALARIO_13(281, "13º salário"),
    FERIAS(300, "Férias"),
    INSS(500, "INSS"),
    IRRF(505, "IRRF"),
    INSS_FERIAS(576, "INSS férias"),
    IRRF_FERIAS(577, "IRRF férias"),
    VALE_ALIMENTACAO(686, "Vale alimentação");

    private final int id;
    private final String description;

    PayrollEvent(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static PayrollEvent get(int id) {
        for (PayrollEvent event : PayrollEvent.values()) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

}
