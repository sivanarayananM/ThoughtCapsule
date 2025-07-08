package com.example.ThoughtCapsule.service;

import com.example.ThoughtCapsule.dto.MessageRequestDto;
import com.example.ThoughtCapsule.dto.MessageResponseDto;
import com.example.ThoughtCapsule.entity.Message;
import com.example.ThoughtCapsule.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Create new message
    public ResponseEntity<String> createMessage(MessageRequestDto dto) {
        try {
            if (dto.getPinCode() == null || dto.getPinCode() < 1000 || dto.getPinCode() > 9999) {
                return ResponseEntity.badRequest().body("❌ A valid 4-digit pinCode is required (1000–9999).");
            }

            // Pin code uniqueness check is removed as per your requirement that pin can be reused.

            Message message = new Message();
            message.setContent(dto.getContent());
            message.setMediaUrl(dto.getMediaUrl());
            message.setUnlockAt(dto.getUnlockAt());
            message.setCreatedAt(LocalDateTime.now());
            message.setReadOnce(dto.isReadOnce());
            message.setRead(false);
            message.setExpired(false);

            message.setPinCode(dto.getPinCode());
            message.setRecipientEmail(dto.getRecipientEmail());
            message.setSenderName(dto.getSenderName());
            message.setSenderEmail("thoughtcapsule.service@gmail.com");  // Set this or get from properties if dynamic

            messageRepo.save(message);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, hh:mm a");
            String formattedDateTime = dto.getUnlockAt().format(formatter);

            return ResponseEntity.ok("✅ Message created! Message ID: " + message.getMessageId() +
                    " 🔒 Locked until: " + formattedDateTime);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to create message: " + e.getMessage());
        }
    }


    // Unlock by ID and PIN code
    public ResponseEntity<MessageResponseDto> unlockByIdAndPin(Long id, Integer pinCode) {
        try {
            Optional<Message> optionalMessage = messageRepo.findByMessageIdAndPinCode(id, pinCode);

            if (optionalMessage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponseDto("❌ Invalid message ID or PIN.", null));
            }

            Message message = optionalMessage.get();

            if (LocalDateTime.now().isBefore(message.getUnlockAt())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy, hh:mm a");
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new MessageResponseDto("🔒 Message unlocks at: " + message.getUnlockAt().format(formatter), null));
            }

            if (message.isReadOnce() && message.isRead()) {
                return ResponseEntity.status(HttpStatus.GONE)
                        .body(new MessageResponseDto("⚠️ Message already read (Read Once).", null));
            }

            // ✅ Mark as read if readOnce is true
            if (message.isReadOnce()) {
                message.setRead(true);
            }

            // ✅ Send unlock email only once
            if (!message.isEmailSent()) {
                sendUnlockNotificationEmail(message);
                message.setEmailSent(true);
            }

            // ✅ Save changes (emailSent / isRead updates)
            messageRepo.save(message);

            return ResponseEntity.ok(new MessageResponseDto(message.getContent(), message.getMediaUrl()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDto("❌ Error unlocking message: " + e.getMessage(), null));
        }
    }

    // Send notification email to recipient when message unlocked
    private void sendUnlockNotificationEmail(Message message) {
        try {
            String to = message.getRecipientEmail();
            if (to == null || to.isEmpty()) return;

            String subject = "📬 Unlocking memories: A Thought capsule has been unlocked!";

            String createdAgo = getTimeAgoOrYear(message.getCreatedAt());
            String createdDate = message.getCreatedAt().format(DateTimeFormatter.ofPattern("d MMMM yyyy"));

            StringBuilder body = new StringBuilder();
            body.append("Hello,\n\n")
                    .append("You have unlocked a Thought capsule message from *").append(message.getSenderName()).append("* 🎁\n")
                    .append("Created on: ").append(createdDate).append("\n\n")
                    .append("💌 Message ID: ").append(message.getMessageId()).append("\n")
                    .append("🔐 PIN Code: ").append(message.getPinCode()).append("\n\n")
                    .append("📝 Message:\n")
                    .append(message.getContent()).append("\n");

            if (message.getMediaUrl() != null && !message.getMediaUrl().isBlank()) {
                body.append("\n📎 Media Link: ").append(message.getMediaUrl()).append("\n");
            }

            // Footer message
            body.append("\n\n")
                    .append("- - - - - - - - - - - - - - - - - - - - - - - - - - -\n")
                    .append("📦 This message was sent via Thought Capsule\n")
                    .append("A time-locked message delivery service that lets people send memories into the future.\n")
                    .append("🔐 Secure your thoughts today — unlock them when the time is right!");

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setFrom(message.getSenderEmail());  // Sender email must be set and valid
            email.setSubject(subject);
            email.setText(body.toString());

            mailSender.send(email);
            System.out.println("✅ Unlock notification email sent to " + to);

        } catch (Exception e) {
            System.err.println("❌ Failed to send unlock notification email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getTimeAgoOrYear(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        int years = now.getYear() - createdAt.getYear();
        if (years >= 1) {
            return years + (years == 1 ? " year ago" : " years ago");
        } else {
            return "on " + createdAt.getYear();
        }
    }

}
