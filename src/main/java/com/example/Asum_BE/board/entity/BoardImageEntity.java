package com.example.Asum_BE.board.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardImageEntity {

    private Long imageId;
    private Long boardId;
    private String uploadFileName;
    private String storeFileName;
    private Boolean isDeleted;

    @Builder
    public BoardImageEntity(Long boardId, String uploadFileName, String storeFileName) {
        this.boardId = boardId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
