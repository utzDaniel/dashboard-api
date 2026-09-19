package br.com.dashboard.modules.expense.dto;

import java.time.LocalDate;
import java.util.List;

public record ExpenseSummaryResponse(
        LocalDate competenceInitial,
        LocalDate competenceEnd,
        List<ExpenseCategoryResponse> categories
) {
}
