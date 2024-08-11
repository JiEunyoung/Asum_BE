package com.example.myproject.controller;

import com.example.myproject.model.ChatHistoryBean;
import com.example.myproject.model.MessageBean;
import com.example.myproject.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate; // 추가한 import

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ChatController {

	 private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // 추가한 멤버 변수
    
    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageBean sendMessage(MessageBean message) {
        
   
        // 클라이언트에게 알림을 보내려면 다음과 같이 알림 메시지를 만들고 전송합니다.
        String notification =  message.getText();
        notifyClients(notification); // 클라이언트에게 알림 보내기

        chatService.saveChatHistory(message.getRoom_id(), message.getText(), message.getSenderId());
        
        return message;
    }
    
    // 클라이언트에게 알림을 보내는 메소드
    private void notifyClients(String notification) {
    	
    	
    	  System.out.println("보내지는 알림: " + notification);
    	
        // SimpMessagingTemplate을 사용하여 알림을 특정 주제로 전송합니다.
        // 예를 들어, "/topic/notifications" 주제를 만들고 알림을 전송할 수 있습니다.
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
    
    
    @GetMapping("/chat/getChatHistory")
    public List<ChatHistoryBean> getChatHistory(@RequestParam("room_id") int roomId) {
        // 채팅 서비스를 사용하여 채팅 기록을 가져옵니다.
        return chatService.findChatHistoryByRoomId(roomId);
    }
}
