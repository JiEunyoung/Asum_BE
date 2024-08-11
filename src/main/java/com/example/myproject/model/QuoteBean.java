package com.example.myproject.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class QuoteBean {

	private int quote_history_id;
	private String received_quote;  
	private int pro_id;
	private int user_id; 
	private String user_name;
	private int received_quoteNY;
	private String service_category_id;
	
}
