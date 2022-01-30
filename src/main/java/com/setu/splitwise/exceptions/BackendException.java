package com.setu.splitwise.exceptions;

import com.setu.splitwise.dto.ErrorResponseDto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BackendException extends RuntimeException {

    private ErrorResponseDto errorResponseDto;

    public BackendException(ErrorResponseDto errorResponseDto) {
        super(errorResponseDto.getErrorMessage());
       this.errorResponseDto = errorResponseDto;
    }

}
