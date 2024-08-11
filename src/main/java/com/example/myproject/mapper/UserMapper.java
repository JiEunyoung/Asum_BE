package com.example.myproject.mapper;

import com.example.myproject.model.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface UserMapper {

	@Select("select user_name from users where user_email=#{user_email}")
	String checkUserEmailExist(String user_email);
	
	@Insert("insert into users values(user_seq.nextval, #{user_name}, #{user_email},#{user_pwd},#{reportCnt})")
	void addUserInfo(UserBean joinUserBean);
	
	@Select("select * from users where user_email=#{user_email} and user_pwd=#{user_pwd}")
	UserBean getLoginUserInfo(UserBean tempLoginUserBean);
	
	@Update("UPDATE users " +
	        "SET user_name = #{user_name}, user_email = #{user_email}, user_pwd = #{user_pwd} " +
	        "WHERE user_id = #{user_id}")
	void updateAccountUser(UserBean AccountModifyCom); 

}
