package com.setu.splitwise.exceptions;

import com.setu.splitwise.dto.ErrorResponseDto;

public class GroupNotFoundException extends BackendException {

    public GroupNotFoundException(ErrorResponseDto errorResponseDto) {
        super(errorResponseDto);
    }


}
