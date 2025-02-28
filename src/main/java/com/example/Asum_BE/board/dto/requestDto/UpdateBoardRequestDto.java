package com.example.Asum_BE.board.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardRequestDto {

    private String title;
    private String content;
    private List<MultipartFile> addMultipartFiles;
    private List<String> deleteUploadFiles;
}
