package com.setu.splitwise.dto;

import com.setu.splitwise.enums.ExceptionCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ErrorResponseDto {

    private String errorMessage;
    private ExceptionCode errorCode;
    private HttpStatus status;
    private List<ErrorDto> missingFields;
}
