package com.example.Asum_BE.board.mapper;

import com.example.Asum_BE.board.entity.BoardEntity;
import com.example.Asum_BE.board.entity.BoardImageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시글 목록 조회
    List<BoardEntity> findAllPosts();

    // 게시글 조회
    BoardEntity findPostById(Long id);

    // 게시글 저장
    void savePost(BoardEntity boardEntity);

    // 게시글 수정
    void updatePost(BoardEntity boardEntity);

    // 게시글 삭제
    void deletePost(Long id);

    // 게시글 이미지 조회
    List<BoardImageEntity> findImagesById(Long boardId);

    // 게시글 썸네일 이미지 조회
    String findThumbnailById(Long boardId);

    // 게시글 이미지 저장
    void savePostImages(Long boardId, List<BoardImageEntity> boardImageEntities);

    // 게시글 삭제로 인한 이미지 삭제
    void deletePostToImage(Long id);

    // 게시글 수정에서의 이미지 삭제
    void deleteImage(Long boardId, String deleteImage);
}
