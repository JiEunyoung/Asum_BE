package com.example.Asum_BE.comment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentEntity {

    private Long commentId;
    private Long boardId;
    private Long userId;
    private Long parentId;
    private String content;
    private Date createdAt;
    private Boolean isDeleted;

    @Builder
    public CommentEntity(Long commentId, Long boardId, Long userId, Long parentId, String content, Date createdAt, Boolean isDeleted) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public CommentEntity update(String content){
        this.content = content;
        return this;
    }
}
