package com.example.myproject.interceptor;


import com.example.myproject.model.ReportBean;
import com.example.myproject.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class ReportAlarmInterceptor implements HandlerInterceptor {

	private ReportBean alarmReportBean;
	private AdminService adminService;
	
	public ReportAlarmInterceptor(ReportBean alarmReportBean, AdminService adminService) {
		this.alarmReportBean = alarmReportBean;
		this.adminService = adminService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		int CntReport = adminService.getCntReport();
		
		alarmReportBean.setReportCnt(CntReport);
		request.setAttribute("alarmReportBean", alarmReportBean);
		request.setAttribute("adminService", adminService);
		
		request.setAttribute("CntReport", CntReport);
		
		return true;
	}
	
	
}
