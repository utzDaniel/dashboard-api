package br.com.dashboard.modules.payroll.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record PayrollEventResponse(
        int id,
        String name,
        BigDecimal total
) {
    public PayrollEventResponse {
        total = scaleToMoney(total);
    }

    private static BigDecimal scaleToMoney(BigDecimal value) {
        return (value == null ? BigDecimal.ZERO : value).setScale(2, RoundingMode.HALF_UP);
    }
}
