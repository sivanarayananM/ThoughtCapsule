package com.example.ThoughtCapsule.controller;

import com.example.ThoughtCapsule.dto.MessageRequestDto;
import com.example.ThoughtCapsule.dto.MessageResponseDto;
import com.example.ThoughtCapsule.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // ✅ Create a new message
    @PostMapping("/create")
    public ResponseEntity<String> createMessage(@RequestBody MessageRequestDto dto) {
        try {
            return messageService.createMessage(dto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("❌ Error creating message: " + e.getMessage());
        }
    }


    // ✅ Unlock message using Message ID + PIN (only way to unlock)
    @GetMapping("/unlock/{id}/{pinCode}")
    public ResponseEntity<MessageResponseDto> unlockByIdAndPin(
            @PathVariable Long id,
            @PathVariable Integer pinCode
    ) {
        try {
            return messageService.unlockByIdAndPin(id, pinCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponseDto("❌ Error unlocking message: " + e.getMessage(), null));
        }
    }
}
