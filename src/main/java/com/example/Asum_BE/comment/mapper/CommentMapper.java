package com.example.Asum_BE.comment.mapper;

import com.example.Asum_BE.comment.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 게시글에 해당하는 댓글 조회
    List<CommentEntity> findCommentsById(Long boardId);

    // 게시글에 해당하는 대댓글 조회
    List<CommentEntity> findReplyCommentsById(Long boardId);

    // 댓글 조회
    CommentEntity findCommentById(Long commentId);

    // 댓글 저장
    void saveComment(CommentEntity commentEntity);

    // 댓글 수정
    void updateComment(CommentEntity commentEntity);

    // 댓글 삭제
    void deleteComment(Long commentId);

}
