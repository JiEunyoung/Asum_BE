package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class ChatHistoryBean {
	
	private int chat_id; // ID는 시퀀스를 통해 자동 할당
    private int room_id; 
    private String content;
    private LocalDateTime chattime;
    private int senderId;
    private int pro_id;
    
}