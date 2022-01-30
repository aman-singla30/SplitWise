package com.setu.splitwise.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExpenseResponseDto {

    private Long expenseId;
    private Long paidBy;
    private String description;
    private Double amount;
}
