package com.sahilsalunkhe.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String sub, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(sub);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage); // 🔥 actual mail sending line

            log.info("Mail sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Error While Sending Mail : {}", e.getMessage());
        }
    }
}
