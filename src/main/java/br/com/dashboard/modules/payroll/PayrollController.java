package br.com.dashboard.modules.payroll;

import br.com.dashboard.config.TimestampUtils;
import br.com.dashboard.modules.payroll.dto.PayrollSummaryResponse;
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
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("/payroll/{competenceInitial}/{competenceEnd}/summary")
    public ResponseEntity<PayrollSummaryResponse> getPayroll(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("competenceInitial")
            @Pattern(regexp = TimestampUtils.DATA_REGEX, message = "Competence inicial deve estar no formato yyyy-MM-dd")
            String competenceInitial,
            @PathVariable("competenceEnd")
            @Pattern(regexp = TimestampUtils.DATA_REGEX, message = "Competence fim deve estar no formato yyyy-MM-dd")
            String competenceEnd
    ) {
        return ResponseEntity.ok(
                payrollService.getPayrollSummary(
                        jwt,
                        TimestampUtils.parseCompetence(competenceInitial),
                        TimestampUtils.parseCompetence(competenceEnd)
                ));
    }
}
