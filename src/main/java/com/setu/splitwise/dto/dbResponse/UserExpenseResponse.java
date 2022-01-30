package com.setu.splitwise.dto.dbResponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserExpenseResponse {

    private Long userId;
    private double amount;
}
