package com.team17.preProject.helper.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TemporaryPassword {

    private static final char START_LETTER = 'a';
    private static final char END_LETTER = 'z';
    private static final int PASSWORD_LENGTH = 10;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Random random = new Random();

    public PasswordDto create(){
        String generatedPassword = createRandomPassword();
        String encodedPassword = encoder.encode(generatedPassword);

        return new PasswordDto(generatedPassword, encodedPassword);
    }

    private String createRandomPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            sb.append(createRandomLetter());
        }
        return sb.toString();
    }

    private char createRandomLetter() {
        int randomASCIICode = START_LETTER + random.nextInt(END_LETTER - START_LETTER);
        char randomLetter = (char) randomASCIICode;
        return randomLetter;
    }
}
