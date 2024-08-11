package com.example.myproject.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ProProfileBean {

	private String certification_documents_images;
	private String pro_detailed_introduction;
	private int total_experience_period;
	private int pro_id;
	private String pro_name;
	private String active_location;
	private String active_detailcategory1;
	private String active_detailcategory2;
	private String active_detailcategory3;

}