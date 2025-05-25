package com.example.Asum_BE.notification.service;

import com.example.Asum_BE.board.entity.BoardEntity;
import com.example.Asum_BE.board.mapper.BoardMapper;
import com.example.Asum_BE.chat.entity.ChatEntity;
import com.example.Asum_BE.chat.entity.ChatParticipantEntity;
import com.example.Asum_BE.chat.mapper.ChatMapper;
import com.example.Asum_BE.comment.entity.CommentEntity;
import com.example.Asum_BE.common.exception.InvalidQuoteException;
import com.example.Asum_BE.notification.dto.NotificationResponseDto;
import com.example.Asum_BE.notification.entity.NotificationEntity;
import com.example.Asum_BE.notification.mapper.NotificationMapper;
import com.example.Asum_BE.notification.repository.EmitterRepository;
import com.example.Asum_BE.notification.retry.NotificationRetryHandler;
import com.example.Asum_BE.notification.retry.NotificationRetryQueue;
import com.example.Asum_BE.notification.retry.NotificationRetryTask;
import com.example.Asum_BE.notification.retry.NotificationRetryType;
import com.example.Asum_BE.quote.entity.QuoteEntity;
import com.example.Asum_BE.quote.entity.QuoteExpertEntity;
import com.example.Asum_BE.quote.mapper.QuoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements NotificationRetryHandler {

    private final SqlSessionFactory sqlSessionFactory;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private static final int BATCH_SIZE = 1000;

    private final EmitterRepository emitterRepository;
    private final NotificationMapper notificationMapper;
    private final BoardMapper boardMapper;
    private final ChatMapper chatMapper;
    private final QuoteMapper quoteMapper;
    private final NotificationRetryQueue retryQueue;

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

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


    // 견적서 요청 Notification 생성, 저장, 전송(동기)   X
    public void sendQuoteNotification(QuoteEntity entity, String content) {
        // 알림 생성
        List<NotificationEntity> quoteNotifications = createQuoteNotifications(entity, content);

        // 알림 500개씩 저장
        saveNotificationsInBatch(quoteNotifications);

        // (SSE) 알림 전송
        for (NotificationEntity quoteNotification : quoteNotifications) {
            System.out.println("알림 받을 expertId: " + quoteNotification.getReceiverId());

            String id = quoteNotification.getReceiverId().toString();

            // 로그인 한 유저의 SseEmitter 모두 가져오기
            Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
            sseEmitters.forEach((key, emitter) -> {
                        // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                        emitterRepository.saveEventCache(key, quoteNotification);
                        // 데이터 전송
                        sendToClient(emitter, key,
                                new NotificationResponseDto(
                                        quoteNotification.getEventType(),
                                        quoteNotification.getReferenceId(),
                                        quoteNotification.getContent()
                                )
                        );
            });
        }
    }

    // 견적서 요청 Notification 생성 및 저장 -> 견적서 저장에 영향 미침   X
    public List<NotificationEntity> createAndSaveNotification(QuoteEntity entity, String content) {
        // 알림 생성
        List<NotificationEntity> quoteNotifications = createQuoteNotifications(entity, content);

        // 알림 500개씩 저장
        saveNotificationsInBatch(quoteNotifications);

        return quoteNotifications;
    }

    // 견적서 요청 NotificationEntity 생성
    public List<NotificationEntity> createQuoteNotifications(QuoteEntity entity, String content) {
        Map<String, Object> params = new HashMap<>();
        Long userId = entity.getUserId();
        Long categoryId = entity.getCategoryId();

        String genderPreference = quoteMapper.findGenderForExpert(userId, categoryId);

        System.out.println("userId: " + userId);
        System.out.println("categoryId: " + categoryId);
        System.out.println("gender: " + genderPreference);

        params.put("categoryId", categoryId);
        params.put("userId", userId);
        params.put("genderPreference", genderPreference);

        long startTime = System.currentTimeMillis(); // 실행 전 시간 측정

        List<QuoteExpertEntity> quoteExpertsByIdEntity = quoteMapper.findQuoteExpertsById(params);

        long endTime = System.currentTimeMillis(); // 실행 후 시간 측정
        log.info("[TIME] findQuoteExpertsById 실행 시간: {}ms", endTime - startTime);

        if (quoteExpertsByIdEntity == null || quoteExpertsByIdEntity.isEmpty()) {
            throw new InvalidQuoteException(404, "해당 견적 요청서 조건에 만족하는 고수가 존재하지 않습니다.\n다른 조건으로 다시 견적 요청서를 작성해주세요.", HttpStatus.NOT_FOUND);
        }

        return quoteExpertsByIdEntity.stream()
                .map(quoteExpertEntity -> NotificationEntity.builder()
                        .receiverId(quoteExpertEntity.getExpertId())
                        .role("EXPERT")
                        .eventType("QUOTE")
                        .referenceId(quoteExpertEntity.getCategoryId())
                        .content(content)
                        .build())
                .collect(Collectors.toList());

    }

    public void saveAndSendNotification(List<NotificationEntity> quoteNotifications) {

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // batch size 만큼 저장
        for (int i = 0; i < quoteNotifications.size(); i += BATCH_SIZE) {
            int start = i;
            int end = Math.min(i + BATCH_SIZE, quoteNotifications.size());
            List<NotificationEntity> batch = quoteNotifications.subList(start, end);

            futures.add(CompletableFuture.runAsync(() -> saveNotificationsInBatch(batch), executorService));
        }

        // 저장 완료 후 전송
        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenRunAsync(() -> {
                    try {
                        sendNotificationAsync(quoteNotifications);
                    } catch (Exception e) {
                        log.error("알림 전송 중 오류 발생", e);

                        // 알림 실패 시 재시도 큐에 추가
                        for(NotificationEntity notification : quoteNotifications) {
                            retryQueue.add(new NotificationRetryTask(
                                    NotificationRetryType.SEND, notification, this));
                        }
                    }
                }, executorService);
    }

    // 견적서 요청 NotificationEntity 저장
    private void saveNotificationsInBatch(List<NotificationEntity> notificationBatchList) {

        long starts = System.currentTimeMillis();

        try {
            long batchStart = System.currentTimeMillis();
            notificationMapper.saveBatch(notificationBatchList);
            log.info("[TIME] 알림 배치 저장 완료 ({} ~ {}): {}ms",
                    0, notificationBatchList.size(), System.currentTimeMillis() - batchStart);
        } catch (Exception e) {
            log.warn("알림 벌크 저장 실패 -> 개별 저장 시도 (배치 크기: {})", notificationBatchList.size());
            for (NotificationEntity entity : notificationBatchList) {
                try {
                    notificationMapper.save(entity);
                } catch (Exception ex) {
                    log.error("개별 저장 실패 -> 재시도 큐 등록: {}", entity, ex.getMessage());
                    retryQueue.add(new NotificationRetryTask(NotificationRetryType.SAVE, entity, this));
                }
            }
        }

        log.info("[TIME] 전체 알림 저장 작업 완료: {}ms", System.currentTimeMillis() - starts);
    }

    // 알림 전송 비동기 처리
    public void sendNotificationAsync(List<NotificationEntity> notifications) {

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < notifications.size(); i += BATCH_SIZE) {
            int start = i;
            int end = Math.min(i + BATCH_SIZE, notifications.size());
            List<NotificationEntity> batchList = notifications.subList(start, end);

            // 각 배치를 비동기로 처리
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> sendBatchList(batchList), executorService);
            futures.add(future);
        }

        // 모든 배치가 처리된 후 완료를 알림
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void sendBatchList(List<NotificationEntity> notifications) {
        long batchStart = System.currentTimeMillis();

        List<NotificationEntity> failedNotifications = new ArrayList<>();

        // (SSE) 알림 전송
        for (NotificationEntity quoteNotification : notifications) {
            try {
                String id = quoteNotification.getReceiverId().toString();

                // 로그인 한 유저의 SseEmitter 모두 가져오기
                Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
                if(sseEmitters.isEmpty()) {
                    // 해당 유저 FCM 처리 예정
                }

                sseEmitters.forEach((key, emitter) -> {
                    try {
                        emitter.onTimeout(() -> emitterRepository.deleteById(key));
                        emitter.onError((e) -> emitterRepository.deleteById(key));
                        emitter.onCompletion(() -> emitterRepository.deleteById(key));

                        // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                        emitterRepository.saveEventCache(key, quoteNotification);
                        // 데이터 전송
                        sendToClient(emitter, key, new NotificationResponseDto(
                                quoteNotification.getEventType(),
                                quoteNotification.getReferenceId(),
                                quoteNotification.getContent()
                        ));
                    } catch (Exception e) {
                        log.warn("개별 emitter 전송 실패: {}", e.getMessage());
                        failedNotifications.add(quoteNotification);
                    }

                });
            } catch (Exception e) {
                log.warn("알림 전송 실패: {} => {}", quoteNotification, e.getMessage());
                failedNotifications.add(quoteNotification);
            }

            // 실패한 알림만 재시도 큐에 추가
            for (NotificationEntity failedNotification : failedNotifications) {
                retryQueue.add(new NotificationRetryTask(NotificationRetryType.SEND, failedNotification, this));
            }
        }

        log.info("[TIME] 알림 전송 배치 완료 ({}건): {}ms", notifications.size(), System.currentTimeMillis() - batchStart);
    }

    @Override
    public void retrySave(NotificationEntity notification) {
        notificationMapper.save(notification);
    }

    @Override
    public void retrySend(NotificationEntity notification) {
        sendBatchList(List.of(notification));
    }
}