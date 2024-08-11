package com.example.myproject.interceptor;

import java.util.List;

import com.example.myproject.model.AdminBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class TopMenuInterceptor3 implements HandlerInterceptor{
 
	@Resource(name = "adminBean")
	private AdminBean AdminloginBean;
	
	
	public TopMenuInterceptor3(AdminBean AdminloginBean) {
		this.AdminloginBean = AdminloginBean;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
 
		request.setAttribute("AdminloginBean", AdminloginBean); 
		return true;
	}
	
	
}
