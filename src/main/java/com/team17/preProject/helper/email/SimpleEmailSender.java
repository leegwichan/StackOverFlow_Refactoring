package com.team17.preProject.helper.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@AllArgsConstructor
public class SimpleEmailSender implements EmailSendable{

    private final JavaMailSender javaMailSender;

    @Override
    public void send(String to, String subject, String message) throws InterruptedException {
        send(new String[]{to}, subject, message);
    }

    @Override
    public void send(String[] to, String subject, String message) throws InterruptedException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setText(message);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);

        System.out.println("Sent simple email!");
    }
}
