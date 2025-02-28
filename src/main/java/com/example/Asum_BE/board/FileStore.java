package com.example.Asum_BE.board;

import com.example.Asum_BE.board.entity.BoardImageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir + filename;
    }

    public List<BoardImageEntity> storeFiles(Long boardId, List<MultipartFile> multipartFiles) throws IOException {
        List<BoardImageEntity> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                BoardImageEntity boardImageEntity = storeFile(boardId, multipartFile);
                storeFileResult.add(boardImageEntity);
            }
        }

        return storeFileResult;
    }

    public BoardImageEntity storeFile(Long boardId, MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new BoardImageEntity(boardId, originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractedExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractedExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
