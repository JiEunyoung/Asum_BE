package com.example.Asum_BE.comment.controller;

import com.example.Asum_BE.comment.dto.requestDto.CommentRequestDto;
import com.example.Asum_BE.comment.dto.responseDto.CommentResponseDto;
import com.example.Asum_BE.comment.dto.responseDto.CommentsResponseDto;
import com.example.Asum_BE.comment.service.CommentService;
import com.example.Asum_BE.common.dto.responseDto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponseDto<List<CommentsResponseDto>>> findCommentsById(@PathVariable Long boardId) {

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "댓글 조회 성공",
                        commentService.findCommentsById(boardId)
                ));
    }

    // 댓글 저장
    @PostMapping("/api/community/comment")
    public ResponseEntity<ApiResponseDto<CommentRequestDto>> saveComment(@RequestBody CommentRequestDto commentRequestDto) {
        // JWT 사용 예정
        Long author_id = 1L;
        String role = "USER";

        CommentResponseDto commentResponseDto = commentService.saveComment(commentRequestDto, author_id, role);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        201,
                        "댓글 저장 성공",
                        commentRequestDto
                ));
    }

    // 댓글 수정
    @PutMapping("/api/community/comment/{commentId}")
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentRequestDto);

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "댓글 수정 성공",
                        commentResponseDto
                ));
    }

    // 댓글 삭제
    @PatchMapping("/api/community/comment/{commentId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "삭제가 완료되었습니다.",
                        null
                ));
    }
}
