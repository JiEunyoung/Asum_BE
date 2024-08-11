package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.ExpertBean;
import com.example.myproject.model.ProUserBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface ProUserMapper {

	@Select("select pro_name from pro_user where pro_email=#{pro_email}")
	String checkProuserEmailExist(String pro_email);

	@Insert("INSERT INTO pro_user (pro_id, pro_email, pro_name, pro_pwd, pro_gender, active_location, active_detailcategory1, active_detailcategory2, active_detailcategory3,reportCnt) VALUES (pro_seq.nextval, #{pro_email}, #{pro_name}, #{pro_pwd}, #{pro_gender}, #{active_location, jdbcType=VARCHAR}, #{active_detailcategory1, jdbcType=VARCHAR}, #{active_detailcategory2, jdbcType=VARCHAR}, #{active_detailcategory3, jdbcType=VARCHAR},#{reportCnt})")
	void addProuserInfo(ProUserBean joinProuserBean);
	
	@Select("select * from pro_user where pro_email=#{pro_email} and pro_pwd=#{pro_pwd}")
	ProUserBean getLoginProuserInfo(ProUserBean tempLoginUserBean2);

	/*
	@Select("select pro_name from pro_user WHERE pro_name LIKE '%' || #{pro_name} || '%'")
	List<String> getSearchProUserByName(String pro_name);// 검색한 pro_name조회
	*/
	//일류프로필 정보 조회 order by
	@Select("select pro_user.pro_id, pro_detailed_introduction, certification_documents_images, pro_name\r\n"
	 			+ "from pro_user, pro_profile\r\n"
	 			+ "where pro_user.pro_id = pro_profile.pro_id and pro_name LIKE '%' || #{pro_name} || '%'"
	 			+ "order by pro_id asc")
	List<ExpertBean> getSearchProUserByName(String pro_name);
	
	//카
	@Select("select active_detailcategory1 from pro_user where pro_id = #{pro_id}")
	   String getCategory1(Long pro_id);
	//테
	   @Select("select active_detailcategory2 from pro_user where pro_id = #{pro_id}")
	   String getCategory2(Long pro_id);
	//고
	   @Select("select active_detailcategory3 from pro_user where pro_id = #{pro_id}")
	   String getCategory3(Long pro_id);
	
	//2
	@Select("SELECT * FROM pro_user WHERE pro_id = #{pro_id}") 
	ProUserBean getCategoriesByProId(Long proId);
	 
	@Select("SELECT pro_id FROM pro_user WHERE pro_name = #{pro_name}") 
	ProUserBean getDetailCategoriesByName(String pro_name); 
	//1
	@Select("SELECT pro_name FROM pro_user WHERE active_detailcategory1 LIKE '%' || #{active_detailcategory1} || '%' " +
	        "OR active_detailcategory2 LIKE '%' || #{active_detailcategory2} || '%' " +
	        "OR active_detailcategory3 LIKE '%' || #{active_detailcategory3} || '%'")
	List<String> getRecoProUserByName(String reco);  //활동지역 고수 조회 

	
	@Select("select pro_name from pro_user")
	List<ProUserBean> getProUserByName(RowBounds rowBounds); // 모든 pro_name조회
 
	@Select("select count(*) from pro_user")
	int getProCnt();
	
	//////////여기부터 일류찾기 ajax
	
	 //일류프로필 정보 조회 order by
 	@Select("select pro_user.pro_id, pro_detailed_introduction, certification_documents_images, pro_name\r\n"
 			+ "from pro_user, pro_profile\r\n"
 			+ "where pro_user.pro_id = pro_profile.pro_id and active_location = #{active_location, jdbcType=VARCHAR}\r\n"
 			+ "order by pro_id asc")
 	List<ExpertBean> getselectedLocation(@Param("active_location") String active_location);
 /*
	@Select("SELECT pro_name FROM pro_user " +
			 "WHERE active_location = #{active_location, jdbcType=VARCHAR}")
	List<String> getselectedLocation(@Param("active_location") String active_location);
	*/
	
 	@Select("SELECT pro_user.pro_id, pro_detailed_introduction, certification_documents_images, pro_name " +
 	        "FROM pro_user, pro_profile " +
 	        "WHERE pro_user.pro_id = pro_profile.pro_id AND (active_detailcategory1 = #{selectedCategory, jdbcType=VARCHAR} " +
 	        "OR active_detailcategory2 = #{selectedCategory, jdbcType=VARCHAR} " +
 	        "OR active_detailcategory3 = #{selectedCategory, jdbcType=VARCHAR}) " +
 	        "ORDER BY pro_id ASC")
 	List<ExpertBean> getselectedCategory(@Param("selectedCategory") String selectedCategory);
	
 	/*
	@Select("SELECT pro_name FROM pro_user " +
	        "WHERE active_detailcategory1 = #{selectedCategory, jdbcType=VARCHAR} " +
	        "OR active_detailcategory2 = #{selectedCategory, jdbcType=VARCHAR} " +
	        "OR active_detailcategory3 = #{selectedCategory, jdbcType=VARCHAR}")
	List<String> getselectedCategory(@Param("selectedCategory") String selectedCategory);

	@Select("SELECT pro_name FROM pro_user " +
	        "WHERE active_location = #{active_location, jdbcType=VARCHAR} " +
	        "AND (active_detailcategory1 = #{selectedCategory, jdbcType=VARCHAR} " +
	        "OR active_detailcategory2 = #{selectedCategory, jdbcType=VARCHAR} " +
	        "OR active_detailcategory3 = #{selectedCategory, jdbcType=VARCHAR})")
	List<String> getProCategoryAndLocation(@Param("selectedCategory") String selectedCategory, @Param("active_location") String active_location);
  */
 	
 	@Select("SELECT pro_user.pro_id, pro_detailed_introduction, certification_documents_images, pro_name " +
 	        "FROM pro_user, pro_profile " +
 	        "WHERE pro_user.pro_id = pro_profile.pro_id AND active_location = #{active_location, jdbcType=VARCHAR} and(active_detailcategory1 = #{selectedCategory, jdbcType=VARCHAR} " +
 	        "OR active_detailcategory2 = #{selectedCategory, jdbcType=VARCHAR} " +
 	        "OR active_detailcategory3 = #{selectedCategory, jdbcType=VARCHAR}) " +
 	        "ORDER BY pro_id ASC")
	List<ExpertBean> getProCategoryAndLocation(@Param("selectedCategory") String selectedCategory, @Param("active_location") String active_location);

	
	/////////////////////////////////////
	@Update("UPDATE pro_user " +
	        "SET pro_name = #{pro_name}, pro_email = #{pro_email}, pro_pwd = #{pro_pwd} " +
	        "WHERE pro_id = #{pro_id}")
	void ProupdateAccountUser(ProUserBean ProAccountModifyCom); 
	
	@Delete("delete from pro_user where pro_id=#{pro_id}")
	int ProAccountResign(int pro_id);
	
	//사용자 이름 가져오기(일류 프로필)
		@Select("select pro_name \r\n"
				+ "from pro_user\r\n"
				+ "where pro_id = #{pro_id}")
		String getProUserName(int pro_id);
		
		// 사용자 이름 수정
		@Update("UPDATE pro_user\r\n"
		        + "SET pro_name = #{pro_name} \r\n"
		        + "WHERE pro_id = #{pro_id}")
		void modifyProName(@Param("pro_name") String pro_name, @Param("pro_id") int pro_id);

		
		//@Select("select active_detailcategory1, active_detailcategory2, active_detailcategory3 from pro_user where pro_id=#{id}")
		//List<Map<String, String>> getCategoryList(int id);
		
		//@Select("select active_detailcategory1, active_detailcategory2, active_detailcategory3 from pro_user where pro_id=#{pro_id}")
		//ProUserBean getCategoryList(int pro_id);
		
		//카테고리 가져오기
		@Select("select active_detailcategory1 from pro_user where pro_id = #{pro_id}")
		String getCategory11(int pro_id);
		
		@Select("select active_detailcategory2 from pro_user where pro_id = #{pro_id}")
		String getCategory22(int pro_id);
		
		@Select("select active_detailcategory3 from pro_user where pro_id = #{pro_id}")
		String getCategory33(int pro_id);
		
		//카테고리 수정
		// 카테고리 수정
		@Update("UPDATE pro_user\n" +
		        "SET \n" +
		        "    active_detailcategory1 = #{active_detailcategory1}\n" +
		        "WHERE pro_id = #{pro_id}")
		void modifyCategory(@Param("active_detailcategory1") String active_detailcategory1,@Param("pro_id") int pro_id);

		@Update("UPDATE pro_user\n" +
		        "SET \n" +
		        "    active_detailcategory2 = #{active_detailcategory2}\n" +
		        "WHERE pro_id = #{pro_id}")
		void modifyCategory2(@Param("active_detailcategory2") String active_detailcategory1,@Param("pro_id") int pro_id);

		@Update("UPDATE pro_user\n" +
		        "SET \n" +
		        "    active_detailcategory3 = #{active_detailcategory3}\n" +
		        "WHERE pro_id = #{pro_id}")
		void modifyCategory3(@Param("active_detailcategory3") String active_detailcategory1,@Param("pro_id") int pro_id);

		
		@Update("update pro_user\r\n"
				+ "set active_detailcategory1 = null\r\n"
				+ "where pro_id = #{pro_id}")
		void deleteCategory1(int pro_id);
		
		@Update("update pro_user\r\n"
				+ "set active_detailcategory2 = null\r\n"
				+ "where pro_id = #{pro_id}")
		void deleteCategory2(int pro_id);
		
		@Update("update pro_user\r\n"
				+ "set active_detailcategory3 = null\r\n"
				+ "where pro_id = #{pro_id}")
		void deleteCategory3(int pro_id);
		
		//일류 지역명
	    @Select("select active_location \r\n"
	             + "from pro_user\r\n"
	             + "where pro_id = #{pro_id}")
	    String getActive_location(int pro_id);
	    
	    @Update("UPDATE pro_user\r\n"
	             + "SET active_location = #{active_location, jdbcType=VARCHAR}\r\n"
	             + "WHERE pro_id = #{pro_id}")
	    void modifyActive_location(@Param("active_location") String active_location, @Param("pro_id") int pro_id);
	    
	    
	     
	}
