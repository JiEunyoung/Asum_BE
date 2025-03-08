package com.example.Asum_BE.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatParticipantEntity {

    private Long userId;
    private Long expertId;
    private Long senderId;
    private String role;
}
