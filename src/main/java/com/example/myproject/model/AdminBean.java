package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AdminBean {

	private String admin_id ;
	private String admin_pwd ;
	private boolean AdminLogin;

	public AdminBean() {   
		this.AdminLogin = false; 
	}

}
