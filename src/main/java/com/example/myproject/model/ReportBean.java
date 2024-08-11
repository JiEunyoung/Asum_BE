package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Getter
@Setter
@Component
public class ReportBean {

	private int report_id;
    private int board_id;
    private int report_state;
    private String report_msg;
    private Timestamp report_date;
    
    private String title;
    private Integer user_id;
    private Integer pro_id;
	private int user_reportcnt;
    private int pro_reportcnt;

    private String user_writer_name;
    private String pro_writer_name;
    
    private String content;
    private String photos;
    private int ReportCnt;

}