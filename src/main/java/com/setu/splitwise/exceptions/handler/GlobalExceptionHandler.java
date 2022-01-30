package com.setu.splitwise.exceptions.handler;

import com.setu.splitwise.dto.ErrorDto;
import com.setu.splitwise.dto.ErrorResponseDto;
import com.setu.splitwise.exceptions.BackendException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    protected MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("Exception :", ex);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().errorMessage("Resource Not found").build();
        return new ResponseEntity<>(errorResponseDto, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    //Backend Exception handling
    @ExceptionHandler({ BackendException.class })
    public ResponseEntity<Object> handleAllBackendException(final BackendException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("Exception :", ex);
        if(StringUtils.isNotBlank(ex.getErrorResponseDto().getErrorMessage())) {
            logger.info("ErrorDto Response : " + ex.getErrorResponseDto().getErrorMessage());
            return new ResponseEntity<>(ErrorResponseDto.builder().status(ex.getErrorResponseDto().getStatus()).errorCode(ex.getErrorResponseDto().getErrorCode()).errorMessage(ex.getErrorResponseDto().getErrorMessage()).build(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
        } else {
            HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
            logger.info("ErrorDto Response : " + ex.getErrorResponseDto());
            if(Optional.ofNullable(ex.getErrorResponseDto().getStatus()).isPresent()) {
                return new ResponseEntity<>(ex.getErrorResponseDto(), new HttpHeaders(),ex.getErrorResponseDto().getStatus() );
            } else {
                return new ResponseEntity<>(ex.getErrorResponseDto(), new HttpHeaders(),httpStatus);
            }

        }
    }

    /**
     * Exception handler for @RequestBody validation errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(convert(exception.getBindingResult().getAllErrors()));
    }

    protected ErrorResponseDto convert(List<ObjectError> objectErrors) {

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().status(HttpStatus.BAD_REQUEST).build();
        List<ErrorDto> errors = new ArrayList<>();

        for (ObjectError objectError : objectErrors) {

            String message = objectError.getDefaultMessage();
            if (message == null) {
                //when using custom spring validator org.springframework.validation.Validator need to resolve messages manually
                message = messageSource.getMessage(objectError, null);
            }

            ErrorDto error;
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                String value = (fieldError.getRejectedValue() == null ? null : fieldError.getRejectedValue().toString());
                error = new ErrorDto(fieldError.getField(), value, message);
            } else {
                error = new ErrorDto(objectError.getObjectName(), objectError.getCode(), objectError.getDefaultMessage());
            }

            errors.add(error);
        }
        errorResponseDto.setMissingFields(errors);
        return errorResponseDto;
    }
}
