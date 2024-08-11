package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class InterestBean {
	
	private int interest_id;
    private int user_id;
    private int pro_id;
    
    //일류 이름, 프로필, 상세소개
    private String pro_name;
    private String certification_documents_images;
    private String pro_detailed_introduction;
    
    //경력
    private int total_experience_sum;
    
    //리뷰
    private int review_count;
    
}