package com.example.myproject.service;

import java.util.List;

import com.example.myproject.dao.CalendarDAO;
import com.example.myproject.mapper.CalendarMapper;
import com.example.myproject.model.CalendarBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

	@Autowired
	private CalendarDAO calendarDao;
	
	@Autowired
	private CalendarMapper calendarMapper;
	
	public void addCalendarInfo(CalendarBean writeCalendarBean) {
		
		//writeCalendarBean.setUser_id(loginUserBean.getUser_id);
		
		calendarDao.addCalendarInfo(writeCalendarBean);
	}
	
	//public List<CalendarBean> getCalendarList() {
		
	//	List<CalendarBean> calendarList = calendarDao.getCalendarList();
	    
	    // 디버깅용 로그
	//    for (CalendarBean list : calendarList) {
	       
	       
	//        	System.out.println("Event: " + list.getCalendar_memo());
	 	        
	       
	//    }
	    
	//    return calendarList;
		
	//}
	
	public List<CalendarBean> getCalendarList(Integer user_id, Integer pro_id) {
		
		return calendarMapper.getCalendarList(user_id, pro_id);
	}
	
	public void modifyCalendar(CalendarBean modifyCalendarBean) {
		
		calendarDao.modifyCalendar(modifyCalendarBean);
	}
	
	public CalendarBean getCalendarContent(int calendar_Id) {
		
		return calendarDao.getCalendarContent(calendar_Id);
	}
	
	public void delecteCalendar(int calendar_Id) {
		
		calendarDao.delecteCalendar(calendar_Id);
	}
	
	public List<CalendarBean> getAlarmList(Integer user_id, Integer pro_id) {
		
		return calendarDao.getAlarmList(user_id, pro_id);
	}
	
}