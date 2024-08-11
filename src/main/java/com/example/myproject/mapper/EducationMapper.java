package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.EducationBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EducationMapper {

	@Insert("INSERT INTO education (education_id, pro_id, school_name, major_name, admission_date, graduation_date, evidence_image)\r\n"
			+ "VALUES (education_seq.nextval, #{pro_id}, #{school_name}, #{major_name}, #{admission_date, jdbcType=DATE}, #{graduation_date, jdbcType=DATE}, #{evidence_image, jdbcType=VARCHAR})")
	void addEducationInfo(EducationBean postEducationBean);
	
	@Select("select education_id, pro_id, school_name, major_name, admission_date, graduation_date, evidence_image\r\n"
			+ "from education\r\n"
			+ "where pro_id = #{pro_id}")
	List<EducationBean> getEducationList(int pro_id);
	
	@Select("select education_id, pro_id, school_name, major_name, admission_date, graduation_date, evidence_image\r\n"
			+ "from education\r\n"
			+ "where education_id = #{education_id}")
	EducationBean getEducationInfo(int education_id);
	
	
	@Update("UPDATE education\r\n"
			+ "SET\r\n"
			+ "    school_name = #{school_name}, \r\n"
			+ "    major_name = #{major_name}, \r\n"
			+ "    admission_date = #{admission_date, jdbcType=DATE}, \r\n"
			+ "    graduation_date = #{graduation_date, jdbcType=DATE}, \r\n"
			+ "    evidence_image =#{evidence_image, jdbcType=VARCHAR} \r\n"
			+ "WHERE education_id = #{education_id}")
	void modifyEducationInfo(EducationBean educationContentModifyBean);
	
	@Delete("DELETE FROM education\r\n"
			+ "WHERE education_id = #{education_id}")
	void deleteEducation(int education_id);
	
	
}