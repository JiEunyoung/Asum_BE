package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.CalendarBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;


public interface CalendarMapper {

	//@SelectKey(statement = "select calendar_seq.nextval from dual", keyProperty = "calendar_id", before=true, resultType = int.class)
		@Insert("INSERT INTO calendar (calendar_Id, calendar_memo, start_date, end_date, alarm_date,user_id, pro_id) " +
	            "VALUES (calendar_seq.nextval, #{calendar_memo, jdbcType=VARCHAR}, #{start_date, jdbcType=TIMESTAMP}, " +
	            "#{end_date, jdbcType=TIMESTAMP}, #{start_date} - INTERVAL '1' DAY, #{user_id, jdbcType=INTEGER, javaType=Integer}, #{pro_id, jdbcType=INTEGER, javaType=Integer})")  
		void addCalendarInfo(CalendarBean writeCalendarBean);
		
		@Select("select calendar_id, calendar_memo,start_date,end_date from calendar where start_date is not null and calendar_memo is not null and end_date is not null and (user_id = #{user_id, jdbcType=INTEGER} OR pro_id = #{pro_id, jdbcType=INTEGER})")
		List<CalendarBean> getCalendarList(@Param("user_id") Integer user_id, @Param("pro_id") Integer pro_id);
		
		@Update("update calendar set calendar_memo = #{calendar_memo}, start_date = #{start_date}, end_date = #{end_date} where calendar_Id = #{calendar_Id}")
		void modifyCalendar(CalendarBean modifyCalendarBean);
		
		@Select("select calendar_memo, start_date, end_date from calendar where calendar_Id = #{calendar_Id}")
		CalendarBean getCalendarContent(int calendar_Id);
		
		@Delete("delete from calendar where calendar_Id = #{calendar_Id}")
		void delecteCalendar(int calendar_Id);
		
		@Select("SELECT * FROM calendar WHERE alarm_date = TRUNC(SYSDATE) AND (user_id = #{user_id, jdbcType=INTEGER} OR pro_id = #{pro_id, jdbcType=INTEGER})")
		List<CalendarBean> getAlarmList(@Param("user_id") Integer user_id, @Param("pro_id") Integer pro_id);
}
