package com.example.Asum_BE.board.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CursorPageResponseDto<T> {

    private List<T> postList;
    private CursorDto nextCursor;

    @Getter
    @AllArgsConstructor
    public static class CursorDto {
        private LocalDateTime cursorCreatedAt;
        private Long cursorBoardId;
    }
}
