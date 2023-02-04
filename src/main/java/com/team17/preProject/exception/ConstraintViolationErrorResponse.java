package com.team17.preProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ConstraintViolationErrorResponse {

    private String propertyPath;
    private Object rejectedValue;
    private String reason;

    public static List<ConstraintViolationErrorResponse> of(
            Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(constraintViolation -> new ConstraintViolationErrorResponse(
                        constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getInvalidValue().toString(),
                        constraintViolation.getMessage()
                )).collect(Collectors.toList());
    }
}
