package br.com.dashboard.modules.expense.dto;

import java.math.BigDecimal;

public interface ExpenseSummaryDto {

    String getName();

    Integer getCategory();

    BigDecimal getTotal();

}
