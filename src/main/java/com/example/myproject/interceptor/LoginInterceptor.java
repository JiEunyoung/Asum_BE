package com.example.myproject.interceptor;


import com.example.myproject.model.AdminBean;
import com.example.myproject.model.ProUserBean;
import com.example.myproject.model.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
	
	private UserBean loginUserBean;
	private ProUserBean loginProuserBean;
	private AdminBean AdminloginBean;
	
	public LoginInterceptor(UserBean loginUserBean, ProUserBean loginProuserBean, AdminBean AdminloginBean) {
		
		this.loginUserBean = loginUserBean;
		this.loginProuserBean = loginProuserBean;
		this.AdminloginBean = AdminloginBean;
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if(loginUserBean.isUserLogin() == false && loginProuserBean.isProuserLogin() == false && AdminloginBean.isAdminLogin() == false) {
			String contextPath = request.getContextPath();
			//웹브라우져에게 요청하라고 지시함
			response.sendRedirect(contextPath + "/user/not_login");
			//다음 단계로 이동하지 않음
			return false;
		} 
		//로그인 되어 있는 상태
		return true;
	}

	
}