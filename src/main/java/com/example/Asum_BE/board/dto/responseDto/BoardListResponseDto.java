package com.example.Asum_BE.board.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponseDto {

    private Long boardId;
    private String title;
    private String storeFilePath;
    private LocalDateTime createdAt;
    private Long viewCount;
}
