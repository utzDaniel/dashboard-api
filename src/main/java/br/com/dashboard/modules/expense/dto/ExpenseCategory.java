package br.com.dashboard.modules.expense.dto;

import lombok.Getter;

@Getter
public enum ExpenseCategory {

    CUSTO_FIXO(1, "Custo Fixo"),
    CONFORTO(2, "Conforto"),
    PRAZERES(3, "Prazeres"),
    CONHECIMENTO(4, "Conhecimento"),
    EMERGENCIA(5, "Emergência");

    private final int id;

    private final String description;

    ExpenseCategory(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static ExpenseCategory get(int id) {
        for (ExpenseCategory category : ExpenseCategory.values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}
