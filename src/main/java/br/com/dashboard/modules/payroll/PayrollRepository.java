package br.com.dashboard.modules.payroll;

import br.com.dashboard.modules.payroll.dto.PayrollEntity;
import br.com.dashboard.modules.payroll.dto.PayrollSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PayrollRepository extends JpaRepository<PayrollEntity, Long> {

    @Query(value = """
            SELECT
                    p.type as type,
                    p.event as event,
                    SUM(p.quantity * p.amount) AS total
            FROM payroll AS p
            INNER JOIN competence AS c
            		ON p.user_id = c.user_id
                   AND p.competence = c.month_year
            WHERE p.user_id = :userId
              AND p.competence BETWEEN :competenceInitial
                                   AND :competenceEnd
            GROUP BY type, event
            ORDER BY type, event
            """, nativeQuery = true)
    List<PayrollSummaryDto> findAllByUserIdAndCompetence(
            @Param("userId") String userId,
            @Param("competenceInitial") LocalDate competenceInitial,
            @Param("competenceEnd") LocalDate competenceEnd
    );
}
