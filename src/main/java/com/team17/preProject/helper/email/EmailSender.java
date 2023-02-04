package com.team17.preProject.helper.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSender {
    private final EmailSendable emailSendable;

    public void setMailSender(String[] to, String subject, String message) throws MailSendException,
            InterruptedException {
        emailSendable.send(to, subject, message);
    }
    public void setEmailSenderSendOne(String to, String subject, String message) throws MailSendException,
            InterruptedException{
        emailSendable.send(new String[]{to}, subject, message);
    }
}
