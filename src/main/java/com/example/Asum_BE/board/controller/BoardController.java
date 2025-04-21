package com.example.Asum_BE.board.controller;

import com.example.Asum_BE.board.dto.requestDto.BoardRequestDto;
import com.example.Asum_BE.board.dto.requestDto.UpdateBoardRequestDto;
import com.example.Asum_BE.board.dto.responseDto.BoardIdResponseDto;
import com.example.Asum_BE.board.dto.responseDto.BoardListResponseDto;
import com.example.Asum_BE.board.dto.responseDto.BoardResponseDto;
import com.example.Asum_BE.board.service.BoardService;
import com.example.Asum_BE.common.dto.responseDto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 목록 조회
    @GetMapping("/api/community/posts")
    public ResponseEntity<ApiResponseDto<List<BoardListResponseDto>>> findAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "게시글 목록 조회 성공",
                        boardService.findAllPosts(page, size)
                ));
    }

    // 게시글 조회
    @GetMapping("/api/community/post/{boardId}")
    public ResponseEntity<ApiResponseDto<BoardResponseDto>> findPostById(@PathVariable Long boardId){
        boardService.increaseViewCount(boardId);

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "게시글 조회 성공",
                        boardService.findPostById(boardId)
                ));
    }

    // 게시글 저장
    @PostMapping(value = "/api/community/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<BoardIdResponseDto>> savePost(@RequestPart BoardRequestDto boardRequestDto,
                                                                       @RequestPart(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles) throws IOException {
        //JWT 사용 예정
        Long author_id = 1L;
        String role = "USER";

        Long boardId = boardService.savePost(boardRequestDto, multipartFiles, author_id, role);
        BoardIdResponseDto boardIdResponseDto = new BoardIdResponseDto(boardId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        201,
                        "게시글 저장 성공",
                        boardIdResponseDto
                ));
    }

    // 게시글 수정
    @PutMapping(value = "/api/community/post/{boardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<BoardIdResponseDto>> updatePost(@PathVariable Long boardId,
                                        @RequestPart UpdateBoardRequestDto updateBoardRequestDto,
                                        @RequestPart(value = "addMultipartFiles", required = false) List<MultipartFile> addMultipartFiles) throws IOException {
        Long updateBoardId = boardService.updatePost(boardId, updateBoardRequestDto, addMultipartFiles);
        BoardIdResponseDto boardIdResponseDto = new BoardIdResponseDto(updateBoardId);

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "게시글 수정 성공",
                        boardIdResponseDto
                ));
    }

    // 게시글 삭제
    @PatchMapping("/api/community/post/{boardId}")
    public ResponseEntity<ApiResponseDto<Void>> deletePost(@PathVariable Long boardId){
        boardService.deletePost(boardId);

        return ResponseEntity
                .ok(ApiResponseDto.success(
                        200,
                        "게시글이 삭제되었습니다.",
                        null
                ));
    }
}