package com.team17.preProject.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class checkController {

    @GetMapping
    public ResponseEntity home(){
        return new ResponseEntity<>("hello Spring", HttpStatus.CREATED);
    }
}
