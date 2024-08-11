package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Component
public class EducationBean {

	private int education_id;
    private int pro_id;
    private String school_name;
    private String major_name;
    private String evidence_image;
	
	private Integer admissionYear;
	private Integer admissionMonth;
	private Date admission_date;

	private Integer graduationYear;
	private Integer graduationMonth;
	private Date graduation_date;
	
	public void updateAdmissionDate() {
        // 연도와 월 정보를 이용하여 Date 객체 생성
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, admissionYear);
        calendar.set(Calendar.MONTH, admissionMonth - 1); // Calendar의 월은 0부터 시작하므로 -1 처리
        this.admission_date = calendar.getTime();
    }
	
	public void updateGraduationDate() {
        // 연도와 월 정보를 이용하여 Date 객체 생성
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, graduationYear);
        calendar.set(Calendar.MONTH, graduationMonth - 1); // Calendar의 월은 0부터 시작하므로 -1 처리
        this.graduation_date = calendar.getTime();
    }

}