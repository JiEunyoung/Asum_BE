package com.example.Asum_BE.board.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private Long authorId;
    private String title;
    private String content;
    private List<String> storeFilePath;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Long viewCount;
    private String role;
}