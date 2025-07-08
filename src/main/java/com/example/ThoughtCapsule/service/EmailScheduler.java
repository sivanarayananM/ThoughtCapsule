package com.example.ThoughtCapsule.service;

import com.example.ThoughtCapsule.entity.Message;
import com.example.ThoughtCapsule.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EmailScheduler {

    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(fixedRate = 60000) // Every 1 minute
    @Transactional
    public void sendUnlockEmails() {
        try {
            List<Message> messages = messageRepo.findMessagesToSend(LocalDateTime.now());

            for (Message message : messages) {
                if (!message.isEmailSent() && LocalDateTime.now().isAfter(message.getUnlockAt())) {
                    sendEmailToRecipient(message);
                    message.setEmailSent(true);
                    messageRepo.save(message);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send scheduled unlock emails: ", e);
        }
    }

    private void sendEmailToRecipient(Message message) {
        try {
            String to = message.getRecipientEmail();
            if (to == null || to.isEmpty()) {
                logger.warn("Recipient email is missing for message ID {}", message.getMessageId());
                return;
            }

            String subject = "📬 Your Thought Capsule has unlocked!";
            String createdDate = message.getCreatedAt().format(DateTimeFormatter.ofPattern("d MMMM yyyy"));
            String yearsAgo = getTimeAgoOrYear(message.getCreatedAt());

            StringBuilder body = new StringBuilder();
            body.append("Hello,\n\n")
                    .append("You received a Thought Capsule from *").append(message.getSenderName()).append("* 🎁\n")
                    .append("Created on: ").append(createdDate).append("\n\n")
                    .append("💌 Message ID: ").append(message.getMessageId()).append("\n")
                    .append("🔐 PIN Code: ").append(message.getPinCode()).append("\n\n")
                    .append("📝 Message:\n")
                    .append(message.getContent()).append("\n");

            if (message.getMediaUrl() != null && !message.getMediaUrl().isBlank()) {
                body.append("\n📎 Media Link: ").append(message.getMediaUrl()).append("\n");
            }

            // ✅ Footer about project
            body.append("\n\n- - - - - - - - - - - - - - - - - - - - - - - - -\n")
                    .append("📦 This message was sent via *Thought Capsule*\n")
                    .append("A time-locked message delivery service that lets people send memories into the future.\n")
                    .append("🔐 Secure your thoughts today — unlock them when the time is right!\n");
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(body.toString());

            mailSender.send(email);
            logger.info("Unlock email sent to {} for message ID {}", to, message.getMessageId());

        } catch (Exception e) {
            logger.error("Error sending email to recipient for message ID {}: ", message.getMessageId(), e);
        }
    }


    private String getTimeAgoOrYear(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Period period = Period.between(createdAt.toLocalDate(), now.toLocalDate());

        int years = period.getYears();
        if (years >= 1) {
            return years + (years == 1 ? " year ago" : " years ago");
        } else {
            return "on " + createdAt.getYear();
        }
    }
}
