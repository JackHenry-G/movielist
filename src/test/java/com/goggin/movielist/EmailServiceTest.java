package com.goggin.movielist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.goggin.movielist.service.EmailService;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    // @MockBean // mock does not execute the acutal logic of the bean
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testSendEmail() {
        // arrange
        String to = "jackhenryg@hotmail.co.uk";
        String subject = "Test Subject";
        String body = "Test Body";

        // act
        emailService.sendEmail(to, subject, body);

        // assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
