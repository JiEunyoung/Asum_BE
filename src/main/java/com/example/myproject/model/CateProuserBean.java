package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CateProuserBean {

	private int pro_id; 
	private String active_detailcategory1;
	private String active_detailcategory2;
	private String active_detailcategory3;
}