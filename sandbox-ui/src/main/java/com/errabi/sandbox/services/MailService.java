package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Configuration;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ConfigurationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.errabi.sandbox.utils.SandboxConstant.SYSTEM_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final ConfigurationRepository configurationRepository;

    public void sendEmail(String to, String subject, String templateKey, Map<String, String> templateParams) {
        try {
            Configuration config = configurationRepository.findConfigurationByKey(templateKey)
                    .orElseThrow(() -> new RuntimeException("Template not found"));
            String template = config.getValue();
            String content = String.format(template, templateParams.values().toArray());
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new TechnicalException(SYSTEM_ERROR,
                    "FAILED TO SEND THE EMAIL",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
