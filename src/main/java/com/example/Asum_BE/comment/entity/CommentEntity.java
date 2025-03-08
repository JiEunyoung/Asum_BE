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
    private Long authorId;
    private Long parentId;
    private String content;
    private Date createdAt;
    private Boolean isDeleted;
    private String role;

    @Builder
    public CommentEntity(Long commentId, Long boardId, Long authorId, Long parentId, String content, Date createdAt, Boolean isDeleted, String role) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.authorId = authorId;
        this.parentId = parentId;
        this.content = content;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.role = role;
    }

    public CommentEntity update(String content){
        this.content = content;
        return this;
    }
}
