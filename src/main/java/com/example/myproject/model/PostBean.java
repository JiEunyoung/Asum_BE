package com.example.myproject.model;

import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Component
public class PostBean {

	private int board_id;
    private Integer user_id;
	private Integer pro_id; 
    
    @NotBlank
    private String title;
    
    private String photos;  //db에 저장하기 위한 값
    
    private List<MultipartFile> upload_photos;
    
    @NotBlank
    private String content;
    
    private String category;
    private String location;
    private int ReportedPostSt; 
    private Timestamp board_date;
    private String prowriter_name;
    private int viewCnt;
    
    private int commentCnt;
}