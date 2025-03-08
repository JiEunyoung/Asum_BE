package com.example.Asum_BE.board.dto.responseDto;

import lombok.*;

import java.util.Date;
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
    private Date createdAt;
    private Boolean isDeleted;
    private Long viewCount;
    private String role;
}