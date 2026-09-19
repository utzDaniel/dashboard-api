package br.com.dashboard.modules.expense;


import br.com.dashboard.modules.expense.dto.ExpenseEntity;
import br.com.dashboard.modules.expense.dto.ExpenseSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query(value = """
            SELECT
                    e.name as name,
                    e.category as category,
                    SUM(e.amount) AS total
            FROM expense AS e
            INNER JOIN competence AS c
            		ON e.user_id = c.user_id
                   AND e.competence = c.month_year
            INNER JOIN transaction_expense AS te 
            		ON e.id = te.expense
            INNER JOIN transaction_account AS ta 
            		ON te.transaction_account = ta.id
            INNER JOIN account AS a 
            		ON ta.account = a.id
            WHERE e.user_id = :userId
              AND e.competence BETWEEN :competenceInitial
                                   AND :competenceEnd
              AND a.link = 2
            GROUP BY e.name, e.category
            ORDER BY e.name, e.category
            """, nativeQuery = true)
    List<ExpenseSummaryDto> findAllByUserIdAndCompetence(
            @Param("userId") String userId,
            @Param("competenceInitial") LocalDate competenceInitial,
            @Param("competenceEnd") LocalDate competenceEnd
    );

}
