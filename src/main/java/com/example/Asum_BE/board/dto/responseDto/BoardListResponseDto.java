package com.example.Asum_BE.board.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponseDto {

    private Long boardId;
    private String title;
    private String storeFilePath;
    private Date createdAt;
    private Long viewCount;
}
