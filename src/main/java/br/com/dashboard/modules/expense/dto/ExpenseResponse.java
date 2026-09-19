package br.com.dashboard.modules.expense.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ExpenseResponse(
        String name,
        BigDecimal total
) {
    public ExpenseResponse {
        total = scaleToMoney(total);
    }

    private static BigDecimal scaleToMoney(BigDecimal value) {
        return (value == null ? BigDecimal.ZERO : value).setScale(2, RoundingMode.HALF_UP);
    }
}