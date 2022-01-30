package com.setu.splitwise.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TotalExpenseRequestDto {

    private Long userId;
    private Long groupId;
}
