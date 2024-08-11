package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserBean {

	private int user_id;
	private String user_name;
	private String user_email;
	private String user_pwd;  
	private String confirmPassword;
	private boolean userEmailExist;
	private boolean userLogin;
	private int reportCnt; 
	private int postCount;  

	public UserBean() { 
		this.userEmailExist = false; // 초기값 false
		// 초기화, 처음에는 중복검사를 하지 않으므로 디폴트 값을 false로 주입
		this.userLogin = false;
		// 초기화, 로그아웃 상태
	}

}
