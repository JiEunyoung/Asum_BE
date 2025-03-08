package com.example.Asum_BE.board.controller;

import com.example.Asum_BE.board.dto.requestDto.BoardRequestDto;
import com.example.Asum_BE.board.dto.requestDto.UpdateBoardRequestDto;
import com.example.Asum_BE.board.dto.responseDto.BoardResponseDto;
import com.example.Asum_BE.board.service.BoardService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<BoardResponseDto>> findAllPosts() {
        return ResponseEntity.ok(boardService.findAllPosts());
    }

    // 게시글 조회
    @GetMapping("/api/community/post/{boardId}")
    public ResponseEntity<BoardResponseDto> findPostById(@PathVariable Long boardId){
        boardService.increaseViewCount(boardId);

        return ResponseEntity.ok(boardService.findPostById(boardId));
    }

    // 게시글 저장
    @PostMapping(value = "/api/community/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardResponseDto> savePost(@RequestPart BoardRequestDto boardRequestDto,
                                                     @RequestPart(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles) throws IOException {
        //JWT 사용 예정
        Long author_id = 1L;
        String role = "USER";

        BoardResponseDto boardResponseDto = boardService.savePost(boardRequestDto, multipartFiles, author_id, role);

        return ResponseEntity.ok(boardResponseDto);
    }

    // 게시글 수정
    @PutMapping(value = "/api/community/post/{boardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePost(@PathVariable Long boardId,
                                        @RequestPart UpdateBoardRequestDto updateBoardRequestDto,
                                        @RequestPart(value = "addMultipartFiles", required = false) List<MultipartFile> addMultipartFiles) throws IOException {
        BoardResponseDto boardResponseDto = boardService.updatePost(boardId, updateBoardRequestDto, addMultipartFiles);

        return ResponseEntity.ok(boardResponseDto);
    }

    // 게시글 삭제
    @PatchMapping("/api/community/post/{boardId}")
    public ResponseEntity<String> deletePost(@PathVariable Long boardId){
        boardService.deletePost(boardId);

        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
}