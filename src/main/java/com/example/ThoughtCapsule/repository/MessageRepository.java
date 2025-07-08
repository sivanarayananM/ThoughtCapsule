package com.example.ThoughtCapsule.repository;

import com.example.ThoughtCapsule.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByPinCode(Integer pinCode);

    Optional<Message> findByMessageIdAndPinCode(Long id, Integer pinCode);

    @Query("SELECT m FROM Message m WHERE m.unlockAt <= :now AND m.isExpired = false AND m.isRead = false AND m.emailSent = false")
    List<Message> findMessagesToSend(@Param("now") LocalDateTime now);

    // Optional: Just in case we need to validate if a pin already exists for a different message
    @Query("SELECT m FROM Message m WHERE m.pinCode = :pinCode AND m.messageId <> :messageId")
    Optional<Message> findOtherMessageWithSamePin(@Param("pinCode") Integer pinCode, @Param("messageId") Long messageId);
}
