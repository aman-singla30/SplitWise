package com.setu.splitwise.dto;

import com.setu.splitwise.enums.ExpenseType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Data
@Builder
public class ExpenseRequestDto {

    private Double amount;
    private Long paidByUserId;
    private ExpenseType expenseType;
    private String description;
    private Long paidInGroupId;
    private Map<Long, Integer> splits; //UserId,percentage in case of Percent
}
