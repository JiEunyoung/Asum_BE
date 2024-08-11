package com.example.myproject.service;

import java.util.List;

import com.example.myproject.dao.DetailCategoryDAO;
import com.example.myproject.model.DetailCategoryBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DetailCategoryService {
	
	@Autowired
	private DetailCategoryDAO detailCategoryDao;


	public List<DetailCategoryBean> getDetailCategoryList(int service_category_id) {
		
		List<DetailCategoryBean> detailCategoryList = detailCategoryDao.getDetailCategoryList(service_category_id);
 	
		return detailCategoryList;
	}
	
	public String getServiceCategoryName(int service_category_id) {
		
		return detailCategoryDao.getServiceCategoryName(service_category_id);
	}
	 
	 // 카테고리 검색 자동완성 메서드
    public List<DetailCategoryBean> getAutocompleteSuggestions(String searchKeyword) {
        return detailCategoryDao.getAutocompleteSuggestions(searchKeyword);
    }
}
