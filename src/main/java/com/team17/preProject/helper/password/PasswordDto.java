package com.team17.preProject.helper.password;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordDto {

    private String decodedPassword;
    private String encodedPassword;
}
