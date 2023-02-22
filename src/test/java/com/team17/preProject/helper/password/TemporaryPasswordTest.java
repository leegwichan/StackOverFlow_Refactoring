package com.team17.preProject.helper.password;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(classes = {TemporaryPassword.class})
public class TemporaryPasswordTest {

    @Autowired private TemporaryPassword temporaryPassword;

    @Test
    void createTest() {
        PasswordDto result = temporaryPassword.create();

        assertTrue(result.getDecodedPassword().matches("^[a-z]{10}$"));
        assertTrue(new BCryptPasswordEncoder().matches(
                result.getDecodedPassword(), result.getEncodedPassword()));
    }
}
