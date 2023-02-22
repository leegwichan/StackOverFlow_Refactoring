package com.team17.preProject.helper.email.password;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.email.EmailSendable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {TemporaryPasswordSender.class})
public class TemporaryPasswordSenderTest {

    @Autowired private TemporaryPasswordSender temporaryPasswordSender;
    @MockBean private EmailSendable emailSendable;

    @Test
    void sendTest() {
        assertDoesNotThrow(() -> temporaryPasswordSender.send("email", "abcd"));
    }

    @Test
    void sendTest_whenSendingEmailFail() throws InterruptedException{
        doThrow(IllegalArgumentException.class).when(emailSendable)
                .send(anyString(), anyString(), anyString());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> temporaryPasswordSender.send("email", "abcd"));
        assertThat(result.getMessage()).isEqualTo("서버에서 이메일을 전송하지 못했습니다.");
    }

}
