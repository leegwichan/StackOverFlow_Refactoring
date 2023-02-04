package com.team17.preProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class FieldErrorResponse {

    private String field;
    private Object rejectedObject;
    private String reason;

    public static List<FieldErrorResponse> of (BindingResult bindingResult){
        final List<org.springframework.validation.FieldError> fieldErrors =
                bindingResult.getFieldErrors();

        return fieldErrors.stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getRejectedValue() == null ?
                                "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
