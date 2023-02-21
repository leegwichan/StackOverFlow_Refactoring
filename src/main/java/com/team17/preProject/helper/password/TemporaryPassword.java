package com.team17.preProject.helper.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TemporaryPassword {

    private static final char START_LETTER = 'a';
    private static final char END_LETTER = 'z';
    private static final int PASSWORD_LENGTH = 10;

    private final BCryptPasswordEncoder encoder;
    private final Random random = new Random();

    public TemporaryPassword(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String[] create(){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomLetter =
                    START_LETTER + (int) (random.nextFloat() * (START_LETTER - END_LETTER + 1));
            sb.append((char) randomLetter);
        }

        String generatedPassword = sb.toString();
        String encodedPassword = encoder.encode(generatedPassword);

        return new String[]{generatedPassword, encodedPassword};
    }
}
