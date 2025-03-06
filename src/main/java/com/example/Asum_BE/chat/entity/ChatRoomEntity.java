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
public class ChatRoomEntity {

    private Long roomId;
    private Long userId;
    private Long expertId;
    private Date createdAt;
}
