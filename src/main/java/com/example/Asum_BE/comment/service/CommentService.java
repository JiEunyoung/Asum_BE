package com.example.Asum_BE.comment.service;

import com.example.Asum_BE.comment.dto.requestDto.CommentRequestDto;
import com.example.Asum_BE.comment.dto.responseDto.CommentResponseDto;
import com.example.Asum_BE.comment.dto.responseDto.CommentsResponseDto;
import com.example.Asum_BE.comment.entity.CommentEntity;
import com.example.Asum_BE.comment.mapper.CommentMapper;
import com.example.Asum_BE.exception.InvalidCommentException;
import com.example.Asum_BE.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final NotificationService notificationService;

    // 게시글에 해당하는 댓글 조회
    public List<CommentsResponseDto> findCommentsById(Long boardId) {

        // 댓글 조회
        List<CommentEntity> commentsByIdEntity = commentMapper.findCommentsById(boardId);
        // 대댓글 조회
        List<CommentEntity> replyCommentsByIdEntity = commentMapper.findReplyCommentsById(boardId);

        // 대댓글 parentId로 그룹화
        Map<Long, List<CommentEntity>> replyCommentsGroupedByParentId = replyCommentsByIdEntity
                .stream()
                .collect(
                        Collectors.groupingBy(CommentEntity::getParentId)
                );

        // 댓글, 대댓글 맵핑
        return commentsByIdEntity.stream()
                .map(commentEntity -> {
                    List<CommentEntity> replyCommentEntity = replyCommentsGroupedByParentId.getOrDefault(commentEntity.getCommentId(), List.of());
                    // 대댓글 entity -> dto 변환
                    List<CommentResponseDto> replyCommentResponseDtos = replyCommentEntity.stream()
                            .map(reply -> {
                                return new CommentResponseDto(
                                        reply.getCommentId(),
                                        reply.getAuthorId(),
                                        reply.getParentId(),
                                        reply.getContent(),
                                        reply.getCreatedAt(),
                                        reply.getIsDeleted(),
                                        reply.getRole()
                                );
                            })
                            .collect(Collectors.toList());

                    return new CommentsResponseDto(
                            commentEntity.getCommentId(),
                            commentEntity.getAuthorId(),
                            commentEntity.getParentId(),
                            commentEntity.getContent(),
                            commentEntity.getCreatedAt(),
                            commentEntity.getIsDeleted(),
                            replyCommentResponseDtos
                    );
                })
                .collect(Collectors.toList());
    }

    // 댓글 저장
    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto commentRequestDto, Long authorId, String role) {

        if(commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
            throw new InvalidCommentException("댓글은 필수 입력 항목입니다.");
        }

        CommentEntity commentEntity = CommentEntity.builder()
                .boardId(commentRequestDto.getBoardId())
                .authorId(authorId)
                .parentId(commentRequestDto.getParentId())
                .content(commentRequestDto.getContent())
                .role(role)
                .build();
        commentMapper.saveComment(commentEntity);

        CommentEntity commentByIdEntity = commentMapper.findCommentById(commentEntity.getCommentId());

        notificationService.sendCommentNotification(commentEntity, "게시글에 새로운 댓글이 달렸습니다.");

        return new CommentResponseDto(
                commentByIdEntity.getCommentId(),
                commentByIdEntity.getAuthorId(),
                commentByIdEntity.getParentId(),
                commentByIdEntity.getContent(),
                commentByIdEntity.getCreatedAt(),
                commentByIdEntity.getIsDeleted(),
                commentByIdEntity.getRole()
        );
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        CommentEntity commentByIdEntity = commentMapper.findCommentById(commentId);
        if(commentByIdEntity == null || commentByIdEntity.getIsDeleted()){
            throw new InvalidCommentException("해당 댓글이 존재하지 않거나 이미 삭제되었습니다.");
        }

        CommentEntity updateEntity = commentByIdEntity.update(commentRequestDto.getContent());
        commentMapper.updateComment(commentByIdEntity);

        return new CommentResponseDto(
                commentByIdEntity.getCommentId(),
                commentByIdEntity.getAuthorId(),
                commentByIdEntity.getParentId(),
                updateEntity.getContent(),
                commentByIdEntity.getCreatedAt(),
                commentByIdEntity.getIsDeleted(),
                commentByIdEntity.getRole()
        );
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        CommentEntity existedComment = commentMapper.findCommentById(commentId);
        if(existedComment == null || existedComment.getIsDeleted()){
            throw new InvalidCommentException("해당 댓글이 존재하지 않거나 이미 삭제되었습니다.");
        }

        commentMapper.deleteComment(commentId);
    }
}
