package com.example.Asum_BE.chat.service;

import com.example.Asum_BE.chat.dto.requestDto.ChatMessageRequestDto;
import com.example.Asum_BE.chat.dto.requestDto.ChatRoomRequestDto;
import com.example.Asum_BE.chat.dto.responseDto.ChatMessageResponseDto;
import com.example.Asum_BE.chat.dto.responseDto.ChatRoomResponseDto;
import com.example.Asum_BE.chat.entity.ChatEntity;
import com.example.Asum_BE.chat.entity.ChatRoomEntity;
import com.example.Asum_BE.chat.mapper.ChatMapper;
import com.example.Asum_BE.exception.InvalidChatException;
import com.example.Asum_BE.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMapper chatMapper;
    private final NotificationService notificationService;

    // 채팅방 생성
    @Transactional
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto chatRoomRequestDto) {
        if(chatRoomRequestDto.getUserId() == null || chatRoomRequestDto.getExpertId() == null) {
            throw new InvalidChatException("채팅방을 생성할 수 없습니다. 다시 시도해주세요.");
        }

        ChatRoomEntity existingChatRoomEntity = chatMapper.findChatRoomById(chatRoomRequestDto.getUserId(), chatRoomRequestDto.getExpertId());
        if(existingChatRoomEntity != null) {
            throw new InvalidChatException("이미 존재하는 채팅방입니다.");
        }

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .userId(chatRoomRequestDto.getUserId())
                .expertId(chatRoomRequestDto.getExpertId())
                .build();

        chatMapper.createChatRoom(chatRoomEntity);

        return new ChatRoomResponseDto(
                chatRoomEntity.getRoomId(),
                chatRoomEntity.getUserId(),
                chatRoomEntity.getExpertId()
        );
    }

    // 채팅 메시지 저장
    @Transactional
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatMessageRequestDto) {
        if(chatMessageRequestDto.getMessage() == null || chatMessageRequestDto.getMessage().trim().isEmpty()) {
            throw new InvalidChatException("메시지를 입력해주세요.");
        }

        ChatEntity chatEntity = ChatEntity.builder()
                .roomId(chatMessageRequestDto.getRoomId())
                .senderId(chatMessageRequestDto.getSenderId())
                .message(chatMessageRequestDto.getMessage())
                .role(chatMessageRequestDto.getRole())
                .build();

        chatMapper.sendMessage(chatEntity);
        ChatEntity sendChatEntity = chatMapper.findMessageById(chatEntity.getChatId());

        notificationService.sendChatNotification(sendChatEntity, "새로운 채팅이 도착했어요.");

        return new ChatMessageResponseDto(
                sendChatEntity.getRoomId(),
                sendChatEntity.getSenderId(),
                sendChatEntity.getMessage(),
                sendChatEntity.getCreatedAt(),
                sendChatEntity.getRole()
        );
    }

    // 모든 채팅 메시지 조회
    public List<ChatMessageResponseDto> findMessagesById(Long roomId) {
        List<ChatEntity> messagesByIdEntity = chatMapper.findMessagesById(roomId);

        return messagesByIdEntity.stream()
                .map(entity -> {
                    return new ChatMessageResponseDto(
                            entity.getRoomId(),
                            entity.getSenderId(),
                            entity.getMessage(),
                            entity.getCreatedAt(),
                            entity.getRole()
                    );
                })
                .collect(Collectors.toList());
    }
}
