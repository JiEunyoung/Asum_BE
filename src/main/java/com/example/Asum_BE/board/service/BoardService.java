package com.example.Asum_BE.board.service;

import com.example.Asum_BE.board.FileStore;
import com.example.Asum_BE.board.dto.requestDto.BoardRequestDto;
import com.example.Asum_BE.board.dto.requestDto.UpdateBoardRequestDto;
import com.example.Asum_BE.board.dto.responseDto.BoardResponseDto;
import com.example.Asum_BE.board.entity.BoardEntity;
import com.example.Asum_BE.board.entity.BoardImageEntity;
import com.example.Asum_BE.board.mapper.BoardMapper;
import com.example.Asum_BE.common.exception.InvalidPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileStore fileStore;

    // 게시글 목록 조회
    public List<BoardResponseDto> findAllPosts(){
        List<BoardEntity> allPostsEntity = boardMapper.findAllPosts();

        // 게시글 썸네일 처리
        return allPostsEntity.stream()
                .map(postEntity -> {
                    String thumbnail = boardMapper.findThumbnailById(postEntity.getBoardId());
                    List<String> thumbnailList = thumbnail != null ? List.of(thumbnail) : Collections.emptyList();

                    return new BoardResponseDto(
                            postEntity.getBoardId(),
                            postEntity.getAuthorId(),
                            postEntity.getTitle(),
                            postEntity.getContent(),
                            thumbnailList,
                            postEntity.getCreatedAt(),
                            postEntity.getIsDeleted(),
                            postEntity.getViewCount(),
                            postEntity.getRole()
                    );
                })
                .collect(Collectors.toList());
    }

    // 게시글 조회
    public BoardResponseDto findPostById(Long boardId) {
        BoardEntity postByIdEntity = boardMapper.findPostById(boardId);
        List<BoardImageEntity> imagesByIdEntity = boardMapper.findImagesById(boardId);

        return new BoardResponseDto(
                postByIdEntity.getBoardId(),
                postByIdEntity.getAuthorId(),
                postByIdEntity.getTitle(),
                postByIdEntity.getContent(),
                imagesByIdEntity.stream().map(BoardImageEntity::getStoreFileName).collect(Collectors.toList()),
                postByIdEntity.getCreatedAt(),
                postByIdEntity.getIsDeleted(),
                postByIdEntity.getViewCount(),
                postByIdEntity.getRole()
        );
    }

    // 게시글 저장
    @Transactional
    public Long savePost(BoardRequestDto boardRequestDto, List<MultipartFile> multipartFiles, Long authorId, String role) throws IOException {

        if(boardRequestDto.getTitle() == null || boardRequestDto.getTitle().trim().isEmpty()) {
            throw new InvalidPostException(400, "제목은 필수 입력 항목입니다.", HttpStatus.BAD_REQUEST);
        }
        if(boardRequestDto.getContent() == null || boardRequestDto.getContent().trim().isEmpty()){
            throw new InvalidPostException(400, "내용은 필수 입력 항목입니다.", HttpStatus.BAD_REQUEST);
        }

        BoardEntity board = BoardEntity.builder()
                .authorId(authorId)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .role(role)
                .build();
        boardMapper.savePost(board);

        List<BoardImageEntity> boardImageEntities = fileStore.storeFiles(board.getBoardId(), multipartFiles);
        if (!boardImageEntities.isEmpty()) {
            boardMapper.savePostImages(board.getBoardId(), boardImageEntities);
        }

        return board.getBoardId();
    }

    // 게시글 수정
    @Transactional
    public Long updatePost(Long boardId, UpdateBoardRequestDto updateBoardRequestDto, List<MultipartFile> addMultipartFiles) throws IOException {
        BoardEntity existedPostEntity = boardMapper.findPostById(boardId);
        if(existedPostEntity == null || existedPostEntity.getIsDeleted()){
            throw new InvalidPostException(404, "해당 게시글이 존재하지 않거나 이미 삭제되었습니다.", HttpStatus.NOT_FOUND);
        }

        // 삭제할 이미지 처리
        if(updateBoardRequestDto.getDeleteUploadFiles() != null) {
            for (String deleteImage : updateBoardRequestDto.getDeleteUploadFiles()) {
                boardMapper.deleteImage(boardId, deleteImage);
            }
        }

        // 추가할 이미지 처리
        if(addMultipartFiles != null){
            List<BoardImageEntity> boardImageEntities = fileStore.storeFiles(boardId, addMultipartFiles);
            boardMapper.savePostImages(boardId, boardImageEntities);
        }

        // 처리 후 게시글에 올라갈 이미지
        List<BoardImageEntity> imagesByIdEntity = boardMapper.findImagesById(boardId);

        BoardEntity updatePostEntity = existedPostEntity.update(updateBoardRequestDto.getTitle(), updateBoardRequestDto.getContent());
        boardMapper.updatePost(updatePostEntity);

        return updatePostEntity.getBoardId();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long boardId){
        BoardResponseDto existedPost = findPostById(boardId);
        if (existedPost == null || existedPost.getIsDeleted()) {
            throw new InvalidPostException(404, "해당 게시글이 존재하지 않거나 이미 삭제되었습니다.", HttpStatus.NOT_FOUND);
        }

        boardMapper.deletePost(boardId);
        boardMapper.deletePostToImage(boardId);
    }

    // 게시글 조회수 증가
    @Transactional
    public void increaseViewCount(Long boardId) {
        boardMapper.increaseViewCount(boardId);
    }
}