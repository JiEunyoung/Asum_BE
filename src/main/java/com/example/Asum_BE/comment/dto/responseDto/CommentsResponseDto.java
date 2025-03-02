package com.example.Asum_BE.comment.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponseDto {

    private Long commentId;
    private Long userId;
    private Long parentId;
    private String content;
    private Date createdAt;
    private Boolean isDeleted;
    private List<CommentResponseDto> replyComments;
}
