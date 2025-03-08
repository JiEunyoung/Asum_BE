package com.example.Asum_BE.board.entity;

import com.example.Asum_BE.board.dto.responseDto.BoardResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardEntity {

    private Long boardId;
    private Long authorId;
    private String title;
    private String content;
    private List<BoardImageEntity> boardImageEntities;
    private Date createdAt;
    private Boolean isDeleted;
    private Long viewCount;
    private String role;

    @Builder
    BoardEntity(Long boardId, Long authorId, String title, String content, List<BoardImageEntity> boardImageEntities, Date createdAt, Boolean isDeleted, Long viewCount, String role) {
        this.boardId = boardId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.boardImageEntities = boardImageEntities;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.viewCount = viewCount;
        this.role = role;
    }

    public BoardEntity update(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }
}
