package com.team17.preProject.helper.email;

import org.springframework.stereotype.Component;

@Component
public interface EmailSendable {

    void send(String[] to, String subject, String Message) throws InterruptedException;
}
