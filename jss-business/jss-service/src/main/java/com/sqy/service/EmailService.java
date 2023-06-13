package com.sqy.service;

import com.sqy.domain.email.Email;
import com.sqy.util.EmailUtils;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendEmail(Email email) {
        log.info("Invoke sendEmailToEmployee({}).", email);
        MimeMessage msg = mailSender.createMimeMessage();

        msg.setRecipients(Message.RecipientType.TO, email.getRecipient());
        msg.setSubject(email.getSubject());
        msg.setContent(email.getHtml(), EmailUtils.TEXT_HTML_UTF_8);

        mailSender.send(msg);
    }

}