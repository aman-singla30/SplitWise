package com.setu.splitwise.exceptions;

import com.setu.splitwise.dto.ErrorResponseDto;

public class UserNotFoundException extends BackendException {

    public UserNotFoundException(ErrorResponseDto errorResponseDto) {
        super(errorResponseDto);
    }

}
