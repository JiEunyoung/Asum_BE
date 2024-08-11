package com.example.myproject.interceptor;

import java.util.List;

import com.example.myproject.model.UserBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class TopMenuInterceptor implements HandlerInterceptor{
 
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	
	
	public TopMenuInterceptor(UserBean loginUserBean) {
		this.loginUserBean = loginUserBean;
	} 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		request.setAttribute("loginUserBean", loginUserBean); 
		return true;
	}
	
	
}
