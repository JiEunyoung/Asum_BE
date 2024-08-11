package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Component
public class CareerBean {

	private int career_id;
	private int pro_id;
	private int total_experience_period;
	private String career_title;
	private String detailed_introduction;

	private Integer startYear;
	private Integer startMonth;
	private Date start_date;

	private Integer endYear;
	private Integer endMonth;
	private Date end_date;
	
	public void updateStartDate() {
        // 연도와 월 정보를 이용하여 Date 객체 생성
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, startYear);
        calendar.set(Calendar.MONTH, startMonth - 1); // Calendar의 월은 0부터 시작하므로 -1 처리
        this.start_date = calendar.getTime();
    }
	
	public void updateEndDate() {
	    if (endYear != null && endMonth != null) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.YEAR, endYear);
	        calendar.set(Calendar.MONTH, endMonth - 1);
	        this.end_date = calendar.getTime();
	    } else {
	        // 값이 설정되지 않은 경우 예외 처리 또는 로깅 등을 수행
	        throw new IllegalArgumentException("endYear 또는 endMonth가 설정되지 않았습니다.");
	    }
	}
 

}