package com.example.myproject.dao;

import java.util.List;

import com.example.myproject.mapper.DetailCategoryMapper;
import com.example.myproject.model.DetailCategoryBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DetailCategoryDAO {

	@Autowired
	private DetailCategoryMapper detailCategoryMapper;
	
	public List<DetailCategoryBean> getDetailCategoryList(int service_category_id) {
		List<DetailCategoryBean> detailCategoryList = detailCategoryMapper.getDetailCategoryList(service_category_id);

		// 각각의 DetailCategoryBean에서 file_name을 출력하는 부분
		//for (DetailCategoryBean detailCategory : detailCategoryList) {
		//	System.out.println("category name: " + detailCategory.getDetail_category_name());
		//	System.out.println("file_name: " + detailCategory.getFile_name());
		//}
		
		
		return detailCategoryList;
	}
	
	public String getServiceCategoryName(int service_category_id) {
		
		return detailCategoryMapper.getServiceCategoryName(service_category_id);
	}
	
	 
	
	 // 자동완성 메서드
    public List<DetailCategoryBean> getAutocompleteSuggestions(String searchKeyword) {
        return detailCategoryMapper.getAutocompleteSuggestions(searchKeyword);
    }
	
}
