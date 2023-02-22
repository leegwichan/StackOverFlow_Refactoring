package com.team17.preProject.helper.email.password;

import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.helper.email.EmailSendable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporaryPasswordSender {

    private static final String EMAIL_TITLE = "TEAM17 StackOverflow 클론 비밀번호 변경되었습니다";
    private static final String EMAIL_CONTENT_FORM =
            "당신의 비밀번호는 %s로 변경되었습니다.\n" +
            "페이지로 가서 비밀번호를 변경해주세요.";

    private final EmailSendable emailSender;

    public void send(String email, String temporaryPassword) {
        try {
            String content  = String.format(EMAIL_CONTENT_FORM, temporaryPassword);
            emailSender.send(email, EMAIL_TITLE, content);
        } catch (InterruptedException e) {
            throw new BusinessLogicException(ExceptionCode.FAIL_SEND_EMAIL_BY_SERVER);
        }
    }
}
