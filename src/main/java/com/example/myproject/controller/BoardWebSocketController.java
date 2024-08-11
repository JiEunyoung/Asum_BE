package com.example.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class BoardWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(BoardWebSocketController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 새 게시글 알림을 처리하는 메소드
    @MessageMapping("/board/newPost")
    public void newPostNotification() { // 메소드 시그니처 변경
        logger.info("새 게시글 알림 발생");
        System.out.println("알림 발생");

        // 메시지 내용을 전송하지 않고, 알림만 브로드캐스트
        String notification = "새 게시글이 등록되었습니다."; // 혹은 다른 고정 메시지나 이벤트 타입
        messagingTemplate.convertAndSend("/topic/boardNotifications", notification);
    }
}