package com.example.myproject.dao;

import com.example.myproject.mapper.UserMapper;
import com.example.myproject.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private UserMapper userMapper;
	
	public String checkUserEmailExist(String user_email) {
		
		return userMapper.checkUserEmailExist(user_email);
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		
		userMapper.addUserInfo(joinUserBean);
	}
	
	public UserBean getLoginUserInfo(UserBean tempLoginUserBean) {
		
		return userMapper.getLoginUserInfo(tempLoginUserBean);
	}
	
	public void updateAccountUser(UserBean userBean)  {
		
		userMapper.updateAccountUser(userBean);
	}
	

}
