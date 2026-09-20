package br.com.dashboard.modules.payroll.dto;

import lombok.Getter;

@Getter
public enum EntryType {

    DESCONTO(1, "Desconto"),
    LIQUIDO(2, "Líquido"),
    BENEFICIO(3, "Benefício");

    private final int id;
    private final String description;

    EntryType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static EntryType get(int id) {
        for (EntryType type : EntryType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

}
