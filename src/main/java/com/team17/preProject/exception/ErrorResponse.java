package com.team17.preProject.exception;

import com.team17.preProject.exception.businessLogic.ExceptionCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String message;

    private ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(ExceptionCode code){
        return new ErrorResponse(code.getStatus(), code.getMessage());
    }
}
