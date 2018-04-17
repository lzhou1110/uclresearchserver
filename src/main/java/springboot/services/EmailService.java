package springboot.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendSimpleMessage(String from,
                           String to,
                           String subject,
                           String text);

    void sendSimpleMessageUsingTemplate(String from,
                                        String to,
                                        String subject,
                                        SimpleMailMessage template,
                                        String... templateArgs);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
}