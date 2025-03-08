package com.example.Asum_BE.notification.service;

import com.example.Asum_BE.board.entity.BoardEntity;
import com.example.Asum_BE.board.mapper.BoardMapper;
import com.example.Asum_BE.chat.entity.ChatEntity;
import com.example.Asum_BE.chat.entity.ChatParticipantEntity;
import com.example.Asum_BE.chat.mapper.ChatMapper;
import com.example.Asum_BE.comment.entity.CommentEntity;
import com.example.Asum_BE.notification.dto.NotificationResponseDto;
import com.example.Asum_BE.notification.entity.NotificationEntity;
import com.example.Asum_BE.notification.mapper.NotificationMapper;
import com.example.Asum_BE.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final NotificationMapper notificationMapper;
    private final BoardMapper boardMapper;
    private final ChatMapper chatMapper;

    public SseEmitter subscribe(Long receiverId, String lastEventId) {
        // 고유한 emitter ID 생성
        String id = receiverId + "_" + System.currentTimeMillis();

        // emitter 생성 및 저장
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        // 연결 종료/타임아웃 시 emitter 제거
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(emitter, id, "EventStream Created. [receiverId=" + receiverId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(receiverId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("SSE 연결 중 오류가 발생했습니다.");
        }
    }

    // 댓글 Notification send
    public void sendCommentNotification(CommentEntity entity, String content) {
        // 알림 생성 및 저장
        NotificationEntity notificationEntity = createCommentNotification(entity, content);
        notificationMapper.save(notificationEntity);

        String id = notificationEntity.getReceiverId().toString();

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, notificationEntity);
                    // 데이터 전송
                    sendToClient(emitter, key, new NotificationResponseDto(notificationEntity.getEventType(), notificationEntity.getReferenceId(), notificationEntity.getContent()));
                }
        );
    }

    // 댓글 NotificationEntity
    private NotificationEntity createCommentNotification(CommentEntity entity, String content) {
        BoardEntity boardEntity = boardMapper.findPostById(entity.getBoardId());

        return NotificationEntity.builder()
                .receiverId(boardEntity.getAuthorId())
                .role(boardEntity.getRole())
                .eventType("COMMENT")
                .referenceId(boardEntity.getBoardId())
                .content(content)
                .build();
    }

    // 채팅 Notification send
    public void sendChatNotification(ChatEntity entity, String content) {
        // 알림 생성 및 저장
        NotificationEntity notificationEntity = createChatNotification(entity, content);
        notificationMapper.save(notificationEntity);

        String id = notificationEntity.getReceiverId().toString();

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, notificationEntity);
                    // 데이터 전송
                    sendToClient(emitter, key, new NotificationResponseDto(notificationEntity.getEventType(), notificationEntity.getReferenceId(), notificationEntity.getContent()));
                }
        );
    }

    // 채팅 NotificationEntity
    private NotificationEntity createChatNotification(ChatEntity entity, String content) {
        ChatParticipantEntity chatParticipantEntity = chatMapper.findParticipantById(entity.getRoomId());

        // sender의 role 확인 및 receiverId 저장
        String role = chatParticipantEntity.getRole();
        Long receiverId = 0L;
        String sseRole = null;

        if(role.equals("USER")) { // USER 채팅 보낸 경우
            receiverId = chatParticipantEntity.getExpertId();
            sseRole = "EXPERT";
        } else {
            receiverId = chatParticipantEntity.getUserId();
            sseRole = "USER";
        }

        return NotificationEntity.builder()
                .receiverId(receiverId)
                .role(sseRole)
                .eventType("CHAT")
                .referenceId(entity.getRoomId())
                .content(content)
                .build();
    }
}