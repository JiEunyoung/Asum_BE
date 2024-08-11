package com.example.myproject.service;

import java.util.List;

import com.example.myproject.dao.CareerDAO;
import com.example.myproject.model.CareerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareerService {
	
	@Autowired
	private CareerDAO careerDao;

	public void addCareerInfo(CareerBean careerBean) {
		
		careerDao.addCareerInfo(careerBean);
	}
	
	public List<CareerBean> getCareerList(int pro_id) {
		
		List<CareerBean> careerList = careerDao.getCareerList(pro_id);

	    

	    return careerList;
	}
	
	public CareerBean getCareerInfo(int career_id) {
		
		return careerDao.getCareerInfo(career_id);
	}
	
	public void modifyCareer(CareerBean careerContentModifyBean) {
		careerDao.modifyCareer(careerContentModifyBean);
	}
	
	public void deleteCareer(int career_id) {
		
		careerDao.deleteCareer(career_id);
	}

}