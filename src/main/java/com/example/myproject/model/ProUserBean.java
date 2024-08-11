package com.example.myproject.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ProUserBean {
	private int pro_id; 
	private String pro_email;
	private int reportCnt;

	@Size(min = 2, max = 8)
	private String pro_name;
	
	private String active_location;
	
	private String[] active_detailcategory = new String[3];
	private String active_detailcategory1;
	private String active_detailcategory2;
	private String active_detailcategory3;
	
	@Size(min = 4, max = 20)
	private String pro_pwd;

	@Size(min = 4, max = 20) 
	private String confirmPassword;
	private String pro_gender;
	private boolean prouserEmailExist;
	private boolean prouserLogin;  
	private int postCount;   
	private String certification_documents_images;

	private int quote_history_id;
	private String received_quote;   

	public ProUserBean() { 
		this.prouserEmailExist = false; // 초기값 false
		// 초기화, 처음에는 중복검사를 하지 않으므로 디폴트 값을 false로 주입
		this.prouserLogin = false;
		// 초기화, 로그아웃 상태
	}

    public void setActive_detailcategory(String[] active_detailcategory) {
    	 
		// 배열의 길이에 따라 각 변수에 값을 할당
		if (active_detailcategory[0]==null) {
			active_detailcategory1 = null;
		}
		active_detailcategory1 = active_detailcategory[0];
	             
		if (active_detailcategory[1]==null) {
			active_detailcategory2 = null;
		}
		active_detailcategory2 = active_detailcategory[1];
            
		if (active_detailcategory[2]==null) {
			active_detailcategory3 = null;
		}
		active_detailcategory3 = active_detailcategory[2];
	}

}
