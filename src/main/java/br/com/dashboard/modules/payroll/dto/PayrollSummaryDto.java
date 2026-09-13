package br.com.dashboard.modules.payroll.dto;

import java.math.BigDecimal;

public interface PayrollSummaryDto {

    Integer getType();

    Integer getEvent();

    BigDecimal getTotal();
}
