package com.setu.splitwise.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupDto {

    private String name;
    private String type;
}
