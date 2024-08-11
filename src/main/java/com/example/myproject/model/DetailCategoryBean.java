package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Component
public class DetailCategoryBean {

	 private int detail_category_id;
	 private int service_category_id;
	 private String detail_category_name;
	 
	 private String file_name; //db 저장
	 private MultipartFile upload_file; // 브라우저가 보낸 파일 데이터
	 
	 private String service_category_name;

}