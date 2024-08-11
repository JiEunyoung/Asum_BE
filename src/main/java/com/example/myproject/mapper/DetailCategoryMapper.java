package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.DetailCategoryBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DetailCategoryMapper {
 
	@Select("select service_category_name\r\n"
			+ "from servicectg\r\n"
			+ "where service_category_id  = #{service_category_id}")
	String getServiceCategoryName(int service_category_id);
	
	@Select("select s.service_category_id, d.detail_category_name, d.file_name, s.service_category_name \r\n"
			+ "from servicectg s, detailctg d\r\n"
			+ "where s.service_category_id = d.service_category_id\r\n"
			+ "and s.service_category_id = #{service_category_id}")
	List<DetailCategoryBean> getDetailCategoryList(int service_category_id);
	          
	                                                                                                   
	// 자동완성 쿼리
	@Select("SELECT detail_category_name, service_category_id\r\n"                                                          
			+ "        FROM detailctg\r\n"                                                             
			+ "        WHERE detail_category_name LIKE '%' || #{searchKeyword} || '%'\r\n"             
			+ "           OR service_category_id IN (\r\n"                                             
			+ "              SELECT service_category_id\r\n"                                           
			+ "              FROM servicectg\r\n"                                                      
			+ "              WHERE service_category_name LIKE '%' || #{searchKeyword} || '%'\r\n"      
			+ "        )")  
    List<DetailCategoryBean> getAutocompleteSuggestions(@Param("searchKeyword") String searchKeyword);
	
	
}
