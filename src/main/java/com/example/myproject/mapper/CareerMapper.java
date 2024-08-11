package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.CareerBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CareerMapper {

	@Insert("INSERT INTO career (career_id, pro_id, total_experience_period, career_title, start_date, end_date, detailed_introduction)\r\n"
	        + "VALUES (career_seq.nextval, #{pro_id}, #{total_experience_period}, #{career_title, jdbcType=VARCHAR}, #{start_date, jdbcType=DATE}, #{end_date, jdbcType=DATE}, #{detailed_introduction, jdbcType=VARCHAR})")
	void addCareerInfo(CareerBean careerBean);
	
	
	@Select("select pro_id, career_id, career_title, start_date, end_date, detailed_introduction, total_experience_period \r\n"
			+ "from career\r\n"
			+ "where pro_id = #{pro_id}")
	List<CareerBean> getCareerList(int pro_id);
	
	@Select("select pro_id,career_id, career_title, start_date, end_date, detailed_introduction, total_experience_period\r\n"
			+ "from career\r\n"
			+ "where career_id = #{career_id}")
	CareerBean getCareerInfo(int career_id);
	
	@Update("UPDATE career\r\n"
			+ "SET \r\n"
			+ "    total_experience_period = #{total_experience_period},\r\n"
			+ "    career_title = #{career_title, jdbcType=VARCHAR},\r\n"
			+ "    start_date = #{start_date, jdbcType=DATE},\r\n"
			+ "    end_date = #{end_date, jdbcType=DATE},\r\n"
			+ "    detailed_introduction = #{detailed_introduction, jdbcType=VARCHAR}\r\n"
			+ "WHERE career_id = #{career_id} ")
	void modifyCareer(CareerBean careerContentModifyBean);
	
	@Delete("delete from career where career_id = #{career_id} ")
	void deleteCareer(int career_id);
	
}