package com.team17.preProject.exception;

import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e){
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity(response,
                HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final List<FieldErrorResponse> response = FieldErrorResponse.of(e.getBindingResult());

        return new ErrorResponseDto(400, response);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleConstraintViolationException(
            ConstraintViolationException e) {
        final List<ConstraintViolationErrorResponse> response = ConstraintViolationErrorResponse.of(e.getConstraintViolations());

        return new ErrorResponseDto<>(400, response);
    }



}
