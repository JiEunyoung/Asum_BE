package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@Component
public class CalendarBean {

	private int calendar_Id;
	private String calendar_memo;

	private Timestamp start_date;
	private Timestamp end_date;
	private Timestamp alarm_date;
	
	private Integer user_id;
	private Integer pro_id;

}