package br.com.dashboard.modules.expense.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record ExpenseCategoryResponse(
        int id,
        String name,
        BigDecimal total,
        List<ExpenseResponse> expenses
) {
    public ExpenseCategoryResponse {
        total = scaleToMoney(total);
    }

    private static BigDecimal scaleToMoney(BigDecimal value) {
        return (value == null ? BigDecimal.ZERO : value).setScale(2, RoundingMode.HALF_UP);
    }
}