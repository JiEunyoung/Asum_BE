package com.example.myproject.dao;

import java.util.List;

import com.example.myproject.mapper.EducationMapper;
import com.example.myproject.model.EducationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EducationDAO {
	
	@Autowired
	EducationMapper educationMapper;

	public void addEducationInfo(EducationBean postEducationBean) {
		
		educationMapper.addEducationInfo(postEducationBean);
	}
	
	public List<EducationBean> getEducationList(int pro_id) {
		
		return educationMapper.getEducationList(pro_id);
	}
	
	public EducationBean getEducationInfo(int education_id) {
		
		return educationMapper.getEducationInfo(education_id);
	}
	
	public void modifyEducationInfo(EducationBean educationContentModifyBean) {
		
		educationMapper.modifyEducationInfo(educationContentModifyBean);
	}
	
	public void deleteEducation(int education_id) {
		
		educationMapper.deleteEducation(education_id);
	}
}