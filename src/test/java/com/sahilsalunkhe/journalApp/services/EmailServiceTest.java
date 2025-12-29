package com.sahilsalunkhe.journalApp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private  EmailService emailService;

    @Test
    void testSendMail(){
            emailService.sendMail("sahilasalunkhe007@gmail.com","help","Hi Sahilya");
    }
}
