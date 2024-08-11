package com.example.myproject.interceptor;

import java.util.List;

import com.example.myproject.model.ProUserBean;
import com.example.myproject.model.QuoteBean;
import com.example.myproject.service.ChatService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class TopMenuInterceptor2 implements HandlerInterceptor{
 
	@Resource(name="loginProuserBean")
	private ProUserBean loginProuserBean;
 
	private ChatService chatservice;
	
	public TopMenuInterceptor2(ProUserBean loginProuserBean, ChatService chatservice) {
		this.loginProuserBean = loginProuserBean;
		this.chatservice=chatservice;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		List<QuoteBean> quoteBean=chatservice.receviedQuote();
		 
		String[] sendQuetes = new String[quoteBean.size()]; // 루프 외부에서 사용되는 변수를 먼저 정의합니다.
	    int i = 0;
	    for(QuoteBean quote:quoteBean) {
	        sendQuetes[i++]=chatservice.getSendQuoteUsername(quote.getQuote_history_id());
	    }
		
		request.setAttribute("sendQuetes", sendQuetes);
		request.setAttribute("quoteBean", quoteBean);
		request.setAttribute("loginProuserBean", loginProuserBean); 
		return true;
	}
	
	
}
