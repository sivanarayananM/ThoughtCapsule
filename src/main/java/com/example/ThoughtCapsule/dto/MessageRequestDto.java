package com.example.ThoughtCapsule.dto;

import java.time.LocalDateTime;

public class MessageRequestDto {

    private String content;
    private String mediaUrl;         // Optional
    private LocalDateTime unlockAt;
    private boolean readOnce;
    private Integer pinCode;
    private String recipientEmail;
    private String senderName;

    // --- Getters and Setters ---
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public LocalDateTime getUnlockAt() {
        return unlockAt;
    }

    public void setUnlockAt(LocalDateTime unlockAt) {
        this.unlockAt = unlockAt;
    }

    public boolean isReadOnce() {
        return readOnce;
    }

    public void setReadOnce(boolean readOnce) {
        this.readOnce = readOnce;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
