package com.example.Asum_BE.chat.controller;

import com.example.Asum_BE.chat.dto.requestDto.ChatMessageRequestDto;
import com.example.Asum_BE.chat.dto.requestDto.ChatRoomRequestDto;
import com.example.Asum_BE.chat.dto.responseDto.ChatMessageResponseDto;
import com.example.Asum_BE.chat.dto.responseDto.ChatRoomResponseDto;
import com.example.Asum_BE.chat.service.ChatService;
import com.example.Asum_BE.common.dto.responseDto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅방 생성
    @PostMapping("/api/chat/room")
    public ResponseEntity<ApiResponseDto<ChatRoomResponseDto>> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        201,
                        "채팅방 생성 성공",
                        chatService.createChatRoom(chatRoomRequestDto)
                ));
    }

    // 채팅 메시지 저장
    @MessageMapping("/api/chat/message")
    @SendTo("/topic/api/chat/message")
    public ResponseEntity<ApiResponseDto<ChatMessageResponseDto>> sendMessage(ChatMessageRequestDto chatMessageRequestDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        201,
                        "채팅 메시지 저장 성공",
                        chatService.sendMessage(chatMessageRequestDto)
                ));
    }

    // 모든 채팅 메시지 조회
    @GetMapping("/api/chat/room/{roomId}")
    public ResponseEntity<ApiResponseDto<List<ChatMessageResponseDto>>> findMessagesById(@PathVariable Long roomId) {

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "채팅 메시지 조회 성공",
                        chatService.findMessagesById(roomId)
                ));
    }
}
