package com.example.myproject.service;

import com.example.myproject.dao.UserDao;
import com.example.myproject.model.UserBean;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Resource(name = "userBean")
	private UserBean loginUserBean;
	
	
	public boolean checkuserEmailExist(String user_email) { 
		String user_name = userDao.checkUserEmailExist(user_email);
		if(user_name == null) {
			return true;
		}else {
			return false;
		}	
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		
		userDao.addUserInfo(joinUserBean);
	}
	 
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		
		UserBean tempLoginUserBean2 = userDao.getLoginUserInfo(tempLoginUserBean);
	 
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_id(tempLoginUserBean2.getUser_id());
			loginUserBean.setUser_email(tempLoginUserBean2.getUser_email());
			loginUserBean.setUser_pwd(tempLoginUserBean2.getUser_pwd());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true);
			
		}else {
		    System.out.println("로그인 사용자 정보가 없습니다.");
		}
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 이메일:"+tempLoginUserBean2.getUser_email());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 이름:"+tempLoginUserBean2.getUser_name());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 번호:"+tempLoginUserBean2.getUser_id());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 비밀번호:"+tempLoginUserBean2.getUser_pwd());
	} 
	
	public void updateAccountUser(UserBean AccountModifyCom)  {
		if(AccountModifyCom != null) {
			loginUserBean.setUser_id(loginUserBean.getUser_id());
			loginUserBean.setUser_email(AccountModifyCom.getUser_email());
			loginUserBean.setUser_pwd(AccountModifyCom.getUser_pwd());
			loginUserBean.setUser_name(AccountModifyCom.getUser_name());
			loginUserBean.setUserLogin(true);
		}
		
		System.out.println("============UserService-updateAccountUser수행========================");
		System.out.println("수정된 아이디:"+AccountModifyCom.getUser_id());
		System.out.println("수정된 이름:"+AccountModifyCom.getUser_name());
		System.out.println("수정된 이메일:"+AccountModifyCom.getUser_email());
		System.out.println("수정된 비밀번호:"+AccountModifyCom.getUser_pwd());
		System.out.println("========================================================================");

		userDao.updateAccountUser(AccountModifyCom);
	}
	 
}

