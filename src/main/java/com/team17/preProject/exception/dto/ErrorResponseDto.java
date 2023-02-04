package com.team17.preProject.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto<T> {
    private int status;
    private T error;
}
