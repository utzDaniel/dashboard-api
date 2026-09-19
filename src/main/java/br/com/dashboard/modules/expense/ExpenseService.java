package br.com.dashboard.modules.expense;


import br.com.dashboard.modules.expense.dto.*;
import br.com.dashboard.modules.keycloak.KeycloakService;
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
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final KeycloakService keycloakService;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            KeycloakService keycloakService
    ) {
        this.expenseRepository = expenseRepository;
        this.keycloakService = keycloakService;
    }

    public ExpenseSummaryResponse getExpenseSummary(Jwt jwt, LocalDate competenceInitial, LocalDate competenceEnd) {
        String userId = keycloakService.getIdUser(jwt);

        return toResponse(
                expenseRepository.
                        findAllByUserIdAndCompetence(userId, competenceInitial, competenceEnd),
                competenceInitial,
                competenceEnd
        );
    }

    private ExpenseSummaryResponse toResponse(List<ExpenseSummaryDto> summaries, LocalDate competenceInitial, LocalDate competenceEnd) {
        Map<Integer, List<ExpenseSummaryDto>> groupedByType = summaries.stream()
                .collect(Collectors.groupingBy(
                        ExpenseSummaryDto::getCategory,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<ExpenseCategoryResponse> entryList = groupedByType.entrySet()
                .stream()
                .map(entry -> {

                    Integer category = entry.getKey();
                    List<ExpenseSummaryDto> items = entry.getValue();

                    List<ExpenseResponse> expenses = items.stream()
                            .map(item -> new ExpenseResponse(
                                    item.getName(),
                                    item.getTotal()
                            ))
                            .toList();

                    BigDecimal total = items.stream()
                            .map(ExpenseSummaryDto::getTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new ExpenseCategoryResponse(
                            category,
                            Objects.requireNonNull(ExpenseCategory.get(category)).getDescription(),
                            total,
                            expenses
                    );
                })
                .toList();

        return new ExpenseSummaryResponse(competenceInitial, competenceEnd, entryList);
    }

}
