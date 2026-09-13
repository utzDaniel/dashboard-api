package br.com.dashboard.modules.payroll.dto;

import java.time.LocalDate;
import java.util.List;

public record PayrollSummaryResponse(
        LocalDate competenceInitial,
        LocalDate competenceEnd,
        List<EntryTypeResponse> entry
) {
}