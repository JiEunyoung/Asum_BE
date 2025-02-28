package com.example.Asum_BE.board.dto.responseDto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

    public Long boardId;
    public Long userId;
    public String title;
    public String content;
    public List<String> storeFilePath;
    public Date createdAt;
    public Boolean isDeleted;
}
