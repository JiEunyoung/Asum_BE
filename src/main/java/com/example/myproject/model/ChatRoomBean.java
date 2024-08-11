package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class ChatRoomBean {

    private int room_id;  // ID는 시퀀스를 통해 자동 할당
    private int user_id;
    private int pro_id;
    private LocalDateTime createdate;
    private String pro_name; // 전문가 이름

}
