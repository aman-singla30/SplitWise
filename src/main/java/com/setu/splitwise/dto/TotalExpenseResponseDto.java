package com.setu.splitwise.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class TotalExpenseResponseDto {

    private Long userId;
    private Long groupId;
    private Double totalPaidAmount;
    private Map<Long, Double> totalExpense;
}
