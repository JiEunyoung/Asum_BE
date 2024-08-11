package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.ServiceCategoryBean;
import org.apache.ibatis.annotations.Select;

public interface ServiceCategoryMapper {

	@Select("select service_category_id, service_category_name from servicectg")
	List<ServiceCategoryBean> getCategoryList();
	 
} 
