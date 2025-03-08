package com.example.Asum_BE.chat.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {

    private Long roomId;
    private Long senderId;
    private String message;
    private Date createdAt;
    private String role;
}
