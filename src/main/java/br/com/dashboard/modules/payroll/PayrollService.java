package br.com.dashboard.modules.payroll;

import br.com.dashboard.modules.keycloak.KeycloakService;
import br.com.dashboard.modules.payroll.dto.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final KeycloakService keycloakService;

    public PayrollService(
            PayrollRepository payrollRepository,
            KeycloakService keycloakService
    ) {
        this.payrollRepository = payrollRepository;
        this.keycloakService = keycloakService;
    }

    public PayrollSummaryResponse getPayrollSummary(Jwt jwt, LocalDate competenceInitial, LocalDate competenceEnd) {
        String userId = keycloakService.getIdUser(jwt);

        return toResponse(
                payrollRepository.
                        findAllByUserIdAndCompetence(userId, competenceInitial, competenceEnd),
                competenceInitial,
                competenceEnd
        );
    }

    private PayrollSummaryResponse toResponse(List<PayrollSummaryDto> summaries, LocalDate competenceInitial, LocalDate competenceEnd) {
        Map<Integer, List<PayrollSummaryDto>> groupedByType = summaries.stream()
                .collect(Collectors.groupingBy(
                        PayrollSummaryDto::getType,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<EntryTypeResponse> entryList = groupedByType.entrySet()
                .stream()
                .map(entry -> {

                    Integer type = entry.getKey();
                    List<PayrollSummaryDto> items = entry.getValue();

                    List<PayrollEventResponse> events = items.stream()
                            .map(item -> new PayrollEventResponse(
                                    item.getEvent(),
                                    Objects.requireNonNull(PayrollEvent.get(item.getEvent())).getDescription(),
                                    item.getTotal()
                            ))
                            .toList();

                    BigDecimal total = items.stream()
                            .map(PayrollSummaryDto::getTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new EntryTypeResponse(
                            type,
                            Objects.requireNonNull(EntryType.get(type)).getDescription(),
                            events,
                            total
                    );
                })
                .toList();

        return new PayrollSummaryResponse(competenceInitial, competenceEnd, entryList);
    }
}
