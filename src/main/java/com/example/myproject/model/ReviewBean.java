package com.example.myproject.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ReviewBean {

    private int review_id;
    private Integer pro_id;
    private Integer user_id; 
   
	@NotBlank
    @Size(min = 10, max = 100)
    private String review_contents;
    private int rating;
    private String photos;
    
    private String review_date;
    
    private String writer_name;
    
    private String category;
    
    private String user_name;
    private String pro_name;
    private int service_category_id;

}