package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MessageBean {
	
    private String text;
    private int senderId;
    private int user_id;
    private int pro_id;
    private int room_id;

}
