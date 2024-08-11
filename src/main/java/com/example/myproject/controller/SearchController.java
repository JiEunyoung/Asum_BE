package com.example.myproject.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.myproject.model.DetailCategoryBean;
import com.example.myproject.model.ExpertBean;
import com.example.myproject.model.PageBean;
import com.example.myproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	ServiceCategoryService serviceCategoryService;
	
	@Autowired
	DetailCategoryService detailCategoryService;
	
	@Autowired
	ProUserService proUserService;
	
	@Autowired
	ProProfileService proprofileservice;
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/findPro")
	public String findPro(@RequestParam(defaultValue = "1") int page, 
            @RequestParam(defaultValue = "10") int size,Model model) {
	    
	    ArrayList<List<DetailCategoryBean>> detailCategoryList = new ArrayList<>();
	    ArrayList<String> service_Category_Name = new ArrayList<>();
	    
	    for (int i = 1; i <= 8; i++) {
	        List<DetailCategoryBean> list1 = detailCategoryService.getDetailCategoryList(i);
	        String categoryName = detailCategoryService.getServiceCategoryName(i);
	        
	        detailCategoryList.add(list1);
	        service_Category_Name.add(categoryName);
	    }
	    
	    PageBean pageBean = proUserService.getProCnt(page);
		model.addAttribute("pageBean", pageBean);
		
		model.addAttribute("page", page);
	    
	    model.addAttribute("detailCategoryList", detailCategoryList);
	    model.addAttribute("service_category_name", service_Category_Name);  
	      
	    model.addAttribute("currentPage", page);
	    
	    //일류 정보 조회 
	    List<ExpertBean> ProprofileInfo=proprofileservice.getProProfileInfo();
	     
	    
	    //프로필
	    model.addAttribute("ProprofileInfo",ProprofileInfo);
	    
	  //경력 정보 조회 
	    List<ExpertBean> CareerInfo=proprofileservice.getCareerInfo();
	    /*
	    for(ExpertBean career: CareerInfo) {
	    	System.out.println("career 총 경력:"+career.getCareer_sum());
	    }*/
	    
	    //경력
	    model.addAttribute("CareerInfo",CareerInfo);
	    
	    //별점조회  
	    List<ExpertBean> reviewList = proprofileservice.getAllReview();
	     /*
	    for(ExpertBean allrei:reviewList) {
	    	System.out.println("allreview 아이디 :"+allrei.getReview_cnt());
	    	System.out.println("---------------------------------------------------------");
	    }*/
	    
	    //리뷰
	    model.addAttribute("reviewList",reviewList);
 
	    
	    return "search/findPro";
	}
	
	@GetMapping("/searchProName")
	public String searchProName(@RequestParam("searchInput") String searchInput,Model model) {
		System.out.println("searchInput:"+searchInput); //받은 값  
		List<ExpertBean> search_proname = proUserService.getSearchProUserByName(searchInput);
		//model.addAttribute("search_proname",search_proname); //유사한 고수 이름  
		//List<String> activeProUser=proUserService.getProCategoryAndLocation(activeData); //전달받은 값  
		System.out.println("유사한 고수 이름:"+search_proname);
		  
	    //일류 정보 조회 
	    //List<ExpertBean> ProprofileInfo=proprofileservice.getProProfileInfo();
		  ArrayList<List<DetailCategoryBean>> detailCategoryList = new ArrayList<>();
		    ArrayList<String> service_Category_Name = new ArrayList<>();
		    
	    for(ExpertBean proprofile:search_proname) {
	    	System.out.println("proprofile 상세설명 :"+proprofile.getPro_detailed_introduction());
	    	System.out.println("proprofile 이미지:"+proprofile.getPro_profile_image());
	    	System.out.println("proprofile 프로아이디:"+proprofile.getPro_id());
	    	System.out.println("proname: " + proprofile.getPro_name());
	    	System.out.println("--------------------------------------------------------------");
	    }
	    
	    //프로필
	    model.addAttribute("search_proname",search_proname);
	    
	    //경력 정보 조회 
	    List<ExpertBean> CareerInfo=proprofileservice.getCareerInfo();
	    for (int i = 1; i <= 8; i++) {
	        List<DetailCategoryBean> list1 = detailCategoryService.getDetailCategoryList(i);
	        String categoryName = detailCategoryService.getServiceCategoryName(i);
	        
	        detailCategoryList.add(list1);
	        service_Category_Name.add(categoryName);
	    }
	     
	    
	    model.addAttribute("detailCategoryList", detailCategoryList);
	    model.addAttribute("service_category_name", service_Category_Name);  
	      
	    
	    //경력
	    model.addAttribute("CareerInfo",CareerInfo);
	    
	    //별점조회  
	    List<ExpertBean> reviewList = proprofileservice.getAllReview();
	     /*
	    for(ExpertBean allrei:reviewList) {
	    	System.out.println("allreview 아이디 :"+allrei.getReview_cnt());
	    	System.out.println("---------------------------------------------------------");
	    }*/
	    
	    //리뷰
	    model.addAttribute("reviewList",reviewList);
 
	      
		return "search/findPro";
	}  
}