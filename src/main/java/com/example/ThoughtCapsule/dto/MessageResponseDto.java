package com.example.ThoughtCapsule.dto;

import java.time.LocalDateTime;

public class MessageResponseDto {
    private String message;
    private String mediaUrl;
    private String senderName;
    private LocalDateTime createdAt;
    private Long messageId;
    private Integer pinCode;

    public MessageResponseDto() {}

    public MessageResponseDto(String message, String mediaUrl) {
        this.message = message;
        this.mediaUrl = mediaUrl;
    }

    public MessageResponseDto(String message, String mediaUrl, String senderName, LocalDateTime createdAt, Long messageId, Integer pinCode) {
        this.message = message;
        this.mediaUrl = mediaUrl;
        this.senderName = senderName;
        this.createdAt = createdAt;
        this.messageId = messageId;
        this.pinCode = pinCode;
    }

    // --- Getters and Setters ---

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }
}
