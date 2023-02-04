package com.team17.preProject.dto;
// org.springframework.http.ResponseEntity

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ResponseData<T> {

    default org.springframework.http.ResponseEntity multi(List<T> data, Page page, HttpStatus status){
        return new org.springframework.http.ResponseEntity(
                new MultiResponseDto<T>(data, page),
                status
        );
    }

    default org.springframework.http.ResponseEntity single(T data, HttpStatus status){
        return new org.springframework.http.ResponseEntity(
                new SingleResponseDto<T>(data),
                status
        );
    }
}
