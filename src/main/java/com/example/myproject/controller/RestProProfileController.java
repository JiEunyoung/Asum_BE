package com.example.myproject.controller;

import java.util.List;

import com.example.myproject.model.ExpertBean;
import com.example.myproject.model.ProUserBean;
import com.example.myproject.service.ProProfileService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pro")
public class RestProProfileController {
	
	@Autowired
	private ProProfileService proProfileService;
	
	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;

	@GetMapping("/expert/events")
	public List<ExpertBean> getExpertInfoList(Model model) {
	    List<ExpertBean> expertInfoList = proProfileService.getExpertInfoList(loginProuserBean.getPro_id());

	    // 가져온 값들을 출력
	    //for (ExpertBean expertBean : expertInfoList) {
	     //   System.out.println("pro_id: " + expertBean.getPro_id());
	     //   System.out.println("pro_detailed_introduction: " + expertBean.getPro_detailed_introduction());
	    //    System.out.println("activity_region: " + expertBean.getActivity_region());
	    //    System.out.println("certification_documents_images: " + expertBean.getCertification_documents_images());
	    //    System.out.println("movable_distance: " + expertBean.getMovable_distance());
	    //    System.out.println("price: " + expertBean.getPrice());
	        // 필요한 다른 속성들도 위와 같이 출력

	        // 빈 줄로 구분
	   //     System.out.println();
	   // }

	    return expertInfoList;
	}
}