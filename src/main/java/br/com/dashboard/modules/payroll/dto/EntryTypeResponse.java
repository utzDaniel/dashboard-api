package br.com.dashboard.modules.payroll.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record EntryTypeResponse(
        int id,
        String name,
        List<PayrollEventResponse> events,
        BigDecimal total
) {
    public EntryTypeResponse {
        total = scaleToMoney(total);
    }

    private static BigDecimal scaleToMoney(BigDecimal value) {
        return (value == null ? BigDecimal.ZERO : value).setScale(2, RoundingMode.HALF_UP);
    }
}
