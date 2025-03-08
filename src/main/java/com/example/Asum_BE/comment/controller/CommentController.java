package com.example.Asum_BE.comment.controller;

import com.example.Asum_BE.comment.dto.requestDto.CommentRequestDto;
import com.example.Asum_BE.comment.dto.responseDto.CommentResponseDto;
import com.example.Asum_BE.comment.dto.responseDto.CommentsResponseDto;
import com.example.Asum_BE.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 게시글에 해당하는 댓글 조회
    @GetMapping("/api/community/post/{boardId}/comments")
    public ResponseEntity<List<CommentsResponseDto>> findCommentsById(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.findCommentsById(boardId));
    }

    // 댓글 저장
    @PostMapping(value = "/api/community/comment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommentRequestDto> saveComment(@RequestBody CommentRequestDto commentRequestDto) {
        // JWT 사용 예정
        Long author_id = 1L;
        String role = "USER";

        CommentResponseDto commentResponseDto = commentService.saveComment(commentRequestDto, author_id, role);

        return ResponseEntity.ok(commentRequestDto);
    }

    // 댓글 수정
    @PutMapping(value = "/api/community/comment/{commentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentRequestDto);
        return ResponseEntity.ok(commentResponseDto);
    }

    // 댓글 삭제
    @PatchMapping("/api/community/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
