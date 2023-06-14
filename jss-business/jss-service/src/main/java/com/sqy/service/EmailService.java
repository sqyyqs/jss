package com.sqy.service;

import com.sqy.domain.email.Email;
import com.sqy.util.EmailUtils;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(Email email) {
        log.info("Invoke sendEmail({}).", email);
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            msg.setRecipients(Message.RecipientType.TO, email.getRecipient());
            msg.setSubject(email.getSubject());
            msg.setContent(email.getHtml(), EmailUtils.TEXT_HTML_UTF_8);
            mailSender.send(msg);
        } catch (MessagingException | MailAuthenticationException e) {
            log.info("Invoke sendEmail({}) with exception.", email, e);
        }
    }

}
