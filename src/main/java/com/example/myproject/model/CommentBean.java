package com.example.myproject.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@Component
public class CommentBean {

	private int comment_id;
	private int board_id;
	private Integer user_id;
	private Integer pro_id;
	
	@NotBlank
	private String comment_content;
	private Timestamp comment_date;
	
	private String comment_writer_name;
	private String comment_prowriter_name;
	
	private int commentCnt; 
	
	private String certification_documents_images;

}