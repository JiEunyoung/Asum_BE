package com.example.myproject.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Component
public class PortFolioBean {
	
	 	private int portfolio_id; //포트폴리오 ID
	 	private int pro_id; //pro_user ID (외래키)
	 	private String service_type; //서비스 종류
	 	private String portfolio_title;// 포트폴리오 제목 
	 	private String detailed_images;// 상세 이미지
	 	private String location_info; // 지역 정보
	 	private int final_amount; //최종 금액
	 	private String work_year; //작업 연도
	 	private String work_period; // 작업 소요 기간
	 	private String detailed_introduction; // 상세 설명
	 	private int inspectionNY;
	 	private int PortfolioCnt; 

		private List<MultipartFile> upload_photos;
	 	
	 	private String test;
	 	
}
