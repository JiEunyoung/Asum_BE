package com.example.Asum_BE.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {

    private Long chatId;
    private Long roomId;
    private Long senderId;
    private String message;
    private Date createdAt;
    private String role;
}
