package com.setu.splitwise.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private String name;
    private String phoneNumber;
    private String email;


}
