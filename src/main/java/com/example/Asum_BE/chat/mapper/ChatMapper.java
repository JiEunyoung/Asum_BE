package com.example.Asum_BE.chat.mapper;

import com.example.Asum_BE.chat.entity.ChatEntity;
import com.example.Asum_BE.chat.entity.ChatRoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    // 채팅방 생성
    void createChatRoom(ChatRoomEntity chatRoomEntity);

    // 생성된 채팅방 조회
    ChatRoomEntity findChatRoomById(Long userId, Long expertId);

    // 채팅 메시지 저장
    void sendMessage(ChatEntity chatEntity);

    // 보낸 메시지 조회
    ChatEntity findMessageById(Long chatId);

    // 모든 채팅 메시지 조회
    List<ChatEntity> findMessagesById(Long roomId);
}
