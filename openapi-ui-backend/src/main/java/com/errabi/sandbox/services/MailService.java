package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Configuration;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ConfigurationRepository;
import com.errabi.sandbox.web.mapper.ConfigurationMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.errabi.sandbox.utils.SandboxConstant.MAIL_SENDING_ERROR_DESCRIPTION;
import static com.errabi.sandbox.utils.SandboxConstant.SYSTEM_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final ConfigurationService configurationService;
    private final ConfigurationRepository configurationRepository;
    private final ConfigurationMapper configurationMapper;

    public void sendEmail(String to, String subject, String templateKey, Map<String, String> templateParams) {
        if (!isEmailServiceEnabled()) {
            log.info("Email service is disabled. No email will be sent.");
            return;
        }
        try {
            Configuration config = configurationMapper.toEntity(configurationService.findConfigByKey(templateKey)) ;
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
                    MAIL_SENDING_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isEmailServiceEnabled() {
        return configurationRepository.findConfigurationByKey("enableEmailService")
                .map(Configuration::getValue)
                .map(Boolean::parseBoolean)
                .orElse(false);
    }
}
