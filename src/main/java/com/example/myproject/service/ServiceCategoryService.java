package com.example.myproject.service;

import java.util.List;

import com.example.myproject.dao.ServiceCategoryDAO;
import com.example.myproject.model.ServiceCategoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCategoryService {

	@Autowired
	private ServiceCategoryDAO serviceCategoryDAO;
	
	public List<ServiceCategoryBean> getCategoryList() {
		
		List<ServiceCategoryBean> categoryList = serviceCategoryDAO.getCategoryList();
 
		return categoryList;
	}
}
