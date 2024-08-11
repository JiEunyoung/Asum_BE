package com.example.myproject.controller;

import java.util.Collections;
import java.util.List;

import com.example.myproject.model.CalendarBean;
import com.example.myproject.model.ProUserBean;
import com.example.myproject.model.UserBean;
import com.example.myproject.service.CalendarService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/common")
public class RestCalendarController {

	@Autowired
	private CalendarService calendarService;
	
	@Resource(name = "userBean")
	private UserBean loginUserBean;
	
	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;
	
	@GetMapping("/calendar_events")
    public List<CalendarBean> getCalendarList(Model model) {
		
		// 현재 로그인한 사용자의 ID 및 프로 회원 ID 가져오기
        //int user_id = loginUserBean.getUser_id();
        //int pro_id = loginProuserBean.getPro_id();
		if (loginUserBean.isUserLogin()) {
            // 일반 회원으로 로그인한 경우
            return calendarService.getCalendarList(loginUserBean.getUser_id(), null);
        } else if (loginProuserBean.isProuserLogin()) {
            // 일류로 로그인한 경우
            return calendarService.getCalendarList(null, loginProuserBean.getPro_id());
        }

        // 로그인 정보가 없는 경우 빈 리스트 반환
        return Collections.emptyList();
       
	}
	
	@GetMapping("/calendar_alarm")
	public List<CalendarBean> getAlarmList(Model model) {
		
		if (loginUserBean.isUserLogin()) {
            // 일반 회원으로 로그인한 경우
            return calendarService.getAlarmList(loginUserBean.getUser_id(), null);
            
        } else if (loginProuserBean.isProuserLogin()) {
            // 일류로 로그인한 경우
            return calendarService.getAlarmList(null, loginProuserBean.getPro_id());
        }
		
		 return Collections.emptyList();
	}
}