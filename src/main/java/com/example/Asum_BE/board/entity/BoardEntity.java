package com.example.Asum_BE.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardEntity {

    private Long boardId;
    private Long authorId;
    private String title;
    private String content;
    private List<BoardImageEntity> boardImageEntities;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Long viewCount;
    private String role;

    @Builder
    BoardEntity(Long boardId, Long authorId, String title, String content, List<BoardImageEntity> boardImageEntities, LocalDateTime createdAt, Boolean isDeleted, Long viewCount, String role) {
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
