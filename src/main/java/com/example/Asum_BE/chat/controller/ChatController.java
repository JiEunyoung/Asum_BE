package com.example.Asum_BE.chat.controller;

import com.example.Asum_BE.chat.dto.requestDto.ChatMessageRequestDto;
import com.example.Asum_BE.chat.dto.requestDto.ChatRoomRequestDto;
import com.example.Asum_BE.chat.dto.responseDto.ChatMessageResponseDto;
import com.example.Asum_BE.chat.dto.responseDto.ChatRoomResponseDto;
import com.example.Asum_BE.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅방 생성
    @PostMapping("/api/chat/room")
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return ResponseEntity.ok(chatService.createChatRoom(chatRoomRequestDto));
    }

    // 채팅 메시지 저장
    @MessageMapping("/api/chat/message")
    @SendTo("/topic/api/chat/message")
    public ResponseEntity<ChatMessageResponseDto> sendMessage(ChatMessageRequestDto chatMessageRequestDto) {
        return ResponseEntity.ok(chatService.sendMessage(chatMessageRequestDto));
    }

    // 모든 채팅 메시지 조회
    @GetMapping("/api/chat/room/{roomId}")
    public ResponseEntity<?> findMessagesById(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatService.findMessagesById(roomId));
    }
}
