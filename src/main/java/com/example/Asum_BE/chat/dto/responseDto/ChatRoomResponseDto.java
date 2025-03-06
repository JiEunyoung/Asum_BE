package com.example.Asum_BE.chat.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto {

    private Long roomId;
    private Long userId;
    private Long expertId;
}
