package br.com.dashboard.modules.expense;

import br.com.dashboard.config.TimestampUtils;
import br.com.dashboard.modules.expense.dto.ExpenseSummaryResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/dashboard")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense/{competenceInitial}/{competenceEnd}/summary")
    public ResponseEntity<ExpenseSummaryResponse> getExpenseSummary(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("competenceInitial")
            @Pattern(regexp = TimestampUtils.DATA_REGEX, message = "Competence inicial deve estar no formato yyyy-MM-dd")
            String competenceInitial,
            @PathVariable("competenceEnd")
            @Pattern(regexp = TimestampUtils.DATA_REGEX, message = "Competence fim deve estar no formato yyyy-MM-dd")
            String competenceEnd
    ) {
        return ResponseEntity.ok(
                expenseService.getExpenseSummary(
                        jwt,
                        TimestampUtils.parseCompetence(competenceInitial),
                        TimestampUtils.parseCompetence(competenceEnd)
                ));
    }

}
