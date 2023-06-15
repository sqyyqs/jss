package com.sqy.unit;

import com.sqy.domain.email.Email;
import com.sqy.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = EmailService.class)
public class EmailServiceTests {

    @MockBean
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmail() {
        Email input = new Email("recipient@example.com", "Test Subject", "<html><body>Test Content</body></html>");
        doNothing().when(mailSender).send(any(MimeMessage.class));
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        emailService.sendEmail(input);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

}
