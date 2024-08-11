package com.example.myproject.dao;

import java.util.List;
import com.example.myproject.model.ServiceCategoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceCategoryDAO {

	@Autowired
	private com.example.myproject.mapper.ServiceCategoryMapper ServiceCategoryMapper;
	
	public List<ServiceCategoryBean> getCategoryList() {
		
		List<ServiceCategoryBean> categoryList = ServiceCategoryMapper.getCategoryList();
        
		
		return categoryList;
	}
	
	
}
