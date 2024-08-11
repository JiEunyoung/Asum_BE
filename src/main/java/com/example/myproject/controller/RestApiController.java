package com.example.myproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.myproject.model.DetailCategoryBean;
import com.example.myproject.model.ExpertBean;
import com.example.myproject.service.DetailCategoryService;
import com.example.myproject.service.ProProfileService;
import com.example.myproject.service.ProUserService;
import com.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProUserService proUserService;
	@Autowired
	ProProfileService proprofileservice;
	
	
	@GetMapping("/user/checkUserEmailExist/{user_email:.+}")
	public boolean checkUserEmailExist(@PathVariable String user_email) {
		
		boolean chk = userService.checkuserEmailExist(user_email);
		System.out.println("RestApiController-checkUserEmailExist: "+chk);
		return chk;
	}
	
	@Autowired
	private ProUserService proUserSerivce;
	
	@GetMapping("/user/checkProuserEmailExist/{pro_email:.+}")
	public boolean checkProuserEmailExist(@PathVariable String pro_email) {
		
		boolean chk = proUserSerivce.checkProuserEmailExist(pro_email);
		System.out.println("RestApiController-checkProuserEmailExist: "+chk);
		return chk;
	}

	@Autowired
	private DetailCategoryService detailCategoryService;
	 
	@GetMapping("/autocomplete") //header.jsp
    public List<DetailCategoryBean> autocomplete(@RequestParam("searchKeyword") String searchKeyword) {
        List<DetailCategoryBean> autocompleteSuggestions = detailCategoryService.getAutocompleteSuggestions(searchKeyword);
        System.out.println("autocompleteSuggestions: " + autocompleteSuggestions);
        System.out.println("autocompleteSuggestions: " + autocompleteSuggestions);
        return autocompleteSuggestions;
    }
	@GetMapping("/search/getCategoryInfo") //findPro.jsp
	public Map<String, List<ExpertBean>> getCategoryInfo(
	        @RequestParam(name = "selectedCategory", required = false) String selectedCategory,
	        @RequestParam(name = "active_location", required = false) String active_location) {

	    System.out.println("RestApiController-selectedCategory: " + selectedCategory);
	    System.out.println("RestApiController-selectedLocation: " + active_location);
	 
	    // 기본값으로 빈 리스트를 설정
	    List<ExpertBean> proActive = new ArrayList<>(); // proActive 선언 및 초기화
	    List<ExpertBean> reviewList = new ArrayList<>(); // proActive 선언 및 초기화
	    List<ExpertBean> CareerInfo = new ArrayList<>(); // proActive 선언 및 초기화
	    
	    if (selectedCategory != null && active_location != null) {
	        // 두 값 모두가 존재할 경우
	       proActive = proUserService.getProCategoryAndLocation(selectedCategory, active_location);
	       reviewList = proprofileservice.getAllReview(); 
		    CareerInfo=proprofileservice.getCareerInfo();
	    } else if (selectedCategory != null) {
	        // selectedCategory만 존재할 경우
	        proActive = proUserService.getselectedCategory(selectedCategory);
	        reviewList = proprofileservice.getAllReview(); 
		    CareerInfo=proprofileservice.getCareerInfo();
	    } else if (active_location != null) {
	        // active_location만 존재할 경우
	        proActive = proUserService.getselectedLocation(active_location);
	        reviewList = proprofileservice.getAllReview(); 
		    CareerInfo=proprofileservice.getCareerInfo();
	    }

	    // 맵에 두개의 리스트를 추가해서 반환
	    Map<String, List<ExpertBean>> result = new HashMap<>();
	    result.put("proActive", proActive);
	    result.put("reviewList", reviewList); 
	    result.put("careerInfo", CareerInfo);
	    return result;
	}

}
