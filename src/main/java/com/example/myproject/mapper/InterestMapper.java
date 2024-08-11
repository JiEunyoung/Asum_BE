package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.InterestBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface InterestMapper {

	@Insert("insert into interests(interest_id, user_id, pro_id) " +
	        "values(interests_seq.nextval, #{user_id}, #{pro_id})")
	void insertInterest(InterestBean interestBean);

	@Delete("delete from interests where user_id = #{user_id} and pro_id = #{pro_id}")
	void deleteInterest(@Param("user_id") int user_id, @Param("pro_id") int pro_id);
	
	@Select("select * from interests where user_id = #{user_id} and "
			+ "pro_id = #{pro_id}")
	InterestBean findInterest(@Param("user_id") int user_id, @Param("pro_id") int pro_id);
	//찜한 일류....프로필 
	
		@Select("SELECT \r\n"
				+ "    interests.pro_id,\r\n"
				+ "    TO_CHAR(pro.pro_detailed_introduction) AS pro_detailed_introduction,\r\n"
				+ "    TO_CHAR(pro.certification_documents_images) AS certification_documents_images,\r\n"
				+ "    p.pro_name,\r\n"
				+ "    interests.interest_id\r\n"
				+ "FROM \r\n"
				+ "    interests\r\n"
				+ "JOIN \r\n"
				+ "    pro_user p ON interests.pro_id = p.pro_id\r\n"
				+ "JOIN \r\n"
				+ "    users u ON interests.user_id = u.user_id\r\n"
				+ "JOIN\r\n"
				+ "    pro_profile pro ON pro.pro_id = p.pro_id\r\n"
				+ "WHERE \r\n"
				+ "    u.user_id = #{user_id}\r\n"
				+ "GROUP BY \r\n"
				+ "    interests.pro_id,\r\n"
				+ "    TO_CHAR(pro.pro_detailed_introduction),\r\n"
				+ "    TO_CHAR(pro.certification_documents_images),\r\n"
				+ "    p.pro_name,\r\n"
				+ "    interests.interest_id\r\n"
				+ "order by interests.pro_id asc")
		List<InterestBean> getProfileList(int user_id);
		 
		//찜한 일류 총경력...
		@Select("SELECT \r\n"
				+ "    SUM(career.total_experience_period) AS total_experience_sum,\r\n"
				+ "    interests.pro_id,\r\n"
				+ "    users.user_id,\r\n"
				+ "    interests.interest_id\r\n"
				+ "FROM \r\n"
				+ "    interests\r\n"
				+ "JOIN \r\n"
				+ "    pro_user ON interests.pro_id = pro_user.pro_id\r\n"
				+ "JOIN \r\n"
				+ "    users ON interests.user_id = users.user_id\r\n"
				+ "LEFT JOIN \r\n"
				+ "    career ON pro_user.pro_id = career.pro_id\r\n"
				+ "WHERE \r\n"
				+ "    users.user_id = #{user_id}\r\n"
				+ "GROUP BY \r\n"
				+ "    interests.pro_id,\r\n"
				+ "    users.user_id,\r\n"
				+ "    interests.interest_id\r\n"
				+ "order by interests.pro_id asc")
		List<InterestBean> getcareerList(int user_id);
		
		//리뷰...
		@Select("SELECT \r\n"
				+ "    COUNT(*) AS review_count,\r\n"
				+ "    interests.pro_id,\r\n"
				+ "    users.user_id,\r\n"
				+ "    interests.interest_id\r\n"
				+ "FROM \r\n"
				+ "    interests\r\n"
				+ "JOIN \r\n"
				+ "    pro_user ON interests.pro_id = pro_user.pro_id\r\n"
				+ "JOIN \r\n"
				+ "    users ON interests.user_id = users.user_id\r\n"
				+ "LEFT JOIN\r\n"
				+ "    review ON pro_user.pro_id = review.pro_id\r\n"
				+ "WHERE \r\n"
				+ "    users.user_id = #{user_id}\r\n"
				+ "GROUP BY \r\n"
				+ "    interests.pro_id,\r\n"
				+ "    users.user_id,\r\n"
				+ "    interests.interest_id\r\n"
				+ "order by interests.pro_id asc")
		List<InterestBean> getReviewList(int user_id);
		 
		//찜한 일류 삭제
		@Delete("DELETE FROM interests\r\n"
				+ "WHERE interest_id = #{interest_id}")
		void InterestDelete(int interest_id);
		
		
	
}