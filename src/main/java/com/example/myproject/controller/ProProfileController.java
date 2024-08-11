package com.example.myproject.controller;


import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.myproject.model.*;
import com.example.myproject.service.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/pro")
public class ProProfileController {
	
	@Autowired
	private CareerService careerService;
	
	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private ProProfileService proProfileService;
	
	@Autowired
	private ProUserService proUserService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@ModelAttribute("totalPeriodOptions")
	public List<Integer> getTotalPeriodOptions() {
	    List<Integer> totalPeriodOptions = new ArrayList<>();
	    for (int i = 1; i <= 30; i++) {
	        totalPeriodOptions.add(i);
	    }
	    return totalPeriodOptions;
	}
	
	@ModelAttribute("yearOptions")
	public List<Integer> getYearOptions() {
	    int currentYear = Year.now().getValue();
	    List<Integer> yearOptions = new ArrayList<>();
	    for (int year = 1980; year <= currentYear; year++) {
	        yearOptions.add(year);
	    }
	    return yearOptions;
	}

	@ModelAttribute("monthOptions")
	public List<Integer> getMonthOptions() {
	    List<Integer> monthOptions = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        monthOptions.add(i);
	    }
	    return monthOptions;
	}


	@GetMapping("/expert")
	public String expert(@ModelAttribute("imageExpertBean") ExpertBean imageExpertBean, Model model, @RequestParam("id") int id,
						 @ModelAttribute("profileImgExpertBean") ExpertBean profileImgExpertBean, @RequestParam("id") int pro_id) {
		
		if(loginProuserBean.isProuserLogin() == true) {
			
			//경력
			List<CareerBean> careerList = careerService.getCareerList(loginProuserBean.getPro_id());
			
			//학력
			List<EducationBean> educationList = educationService.getEducationList(loginProuserBean.getPro_id());
			
			//포트폴리오
			List<PortFolioBean> portfoliList=portfolioService.getPortfolioList(id);
			
			//숨고 활동명
			String pro_name=proUserService.getProUserName(id);
			//System.out.println("pro_name"+pro_name);
			
			//커리어, 학력 년,월
			careerList.forEach(this::extractYearAndMonth);
			educationList.forEach(this::extractYearAndMonth2);
			
			//System.out.println("ppppp:"+pro_name);
			model.addAttribute("pro_name", pro_name);
	        model.addAttribute("careerList", careerList);
	        model.addAttribute("proUserId", loginProuserBean.getPro_id());
	        
	        model.addAttribute("portfoliList",portfoliList);
	        
	        //ProUserBean category = proUserService.getCategoryList(loginProuserBean.getPro_id());

	     // 결과 출력
	     //System.out.println("active_detailcategory1: " + category.getActive_detailcategory1());
	     //System.out.println("active_detailcategory2: " + category.getActive_detailcategory2());
	     //System.out.println("active_detailcategory3: " + category.getActive_detailcategory3());
	     //System.out.println("------------------------");
	        
	        //카테고리 
	        String cate1 = proUserService.getCategory11(id);
	       // System.out.println("cate1 : " + cate1);
	        
	        String cate2 = proUserService.getCategory22(id);
	        //System.out.println("cate2 : " + cate2);
	        
	        String cate3 = proUserService.getCategory33(id);
	        //System.out.println("cate3 : " + cate3);
	        
	        model.addAttribute("cate1", cate1);
	        model.addAttribute("cate2", cate2);
	        model.addAttribute("cate3", cate3);
	        
	      

	        model.addAttribute("educationList", educationList);
	        //model.addAttribute("proUserId", loginProuserBean.getPro_id());
	        
	        //Integer proId = Integer.parseInt(id);
	        imageExpertBean.setPro_id(id);

	        //자격증 이미지
	        imageExpertBean.setPro_id(pro_id);
	        profileImgExpertBean.setPro_id(pro_id);
	        
	        String imageInfo = proProfileService.getImageInfo(pro_id);
	        
	        model.addAttribute("imageInfo", imageInfo);
	        model.addAttribute("profileImgExpertBean", profileImgExpertBean);
	        model.addAttribute("pro_id", pro_id);
	        
	        ExpertBean tempExpertBean = proProfileService.getProfileInfo(pro_id);
	        //tempExpertBean.setCertification_documents_images("dd");
	        
	        //profileImgExpertBean.setCertification_documents_images(tempExpertBean.getCertification_documents_images());
	       
	        
	        String profileImgInfo = proProfileService.getProfileImgInfo(pro_id);
	        model.addAttribute("profileImgInfo", profileImgInfo);
	        
	        //일류 지역
	        String location = proUserService.getActive_location(loginProuserBean.getPro_id());
	           
	         model.addAttribute("location", location);
	        
	        

	        	//for (EducationBean education : educationList) 
	        	//{ 
	        	//	System.out.println("학교: " + education.getSchool_name()); 
	        	//	System.out.println("학과: " + education.getMajor_name()); 
	        	//	System.out.println("시작 날짜: " + education.getAdmissionYear() + "년" + education.getAdmissionMonth()+ "월"); 
	        	//	System.out.println("종료 날짜: " + education.getGraduationYear() + "년" + education.getGraduationMonth() + "월"); 
	        	//	System.out.println("설명: " + education.getEvidence_image()); 
	        	//	System.out.println("login: " + education.getPro_id()); 
	        	//	System.out.println("----------"); 
	        		
	        	//}
		}

	
		return "pro/expert";
	}
	
	/*
	 * @PostMapping(value = "/expert_pro", consumes = "application/json")
	 * 
	 * @ResponseBody public String expert_pro(@RequestBody ExpertBean
	 * writeExpertInfo) {
	 * 
	 * if(loginProuserBean.isProuserLogin()==true) {
	 * 
	 * writeExpertInfo.setPro_id(loginProuserBean.getPro_id()); }
	 * 
	 * proProfileService.addExpertInfo(writeExpertInfo);
	 * 
	 * return "Saved successfully!"; }
	 */
	
	@PostMapping(value = "/expert_introductionModify", consumes = "application/json")
	@ResponseBody
	public String expert_introductionModify(@RequestBody ExpertBean expertBean) {
	    int proIdFromClient = loginProuserBean.getPro_id();

	    // 해당 pro_id에 대한 레코드가 이미 존재하는지 여부 확인
	    boolean recordExists = proProfileService.expertIntroductionExists(proIdFromClient);
	    
	    System.out.println("exist: " + recordExists);

	    // 이미 존재하는 경우 수정, 그렇지 않으면 삽입
	    if (recordExists) {
	        // 이미 레코드가 존재하면 업데이트 수행
	        proProfileService.modifyIntroduction(expertBean.getPro_detailed_introduction(), loginProuserBean.getPro_id());
	        
	        
	    } else {
	        // 레코드가 없으면 삽입 수행
	    	
	    	 if(loginProuserBean.isProuserLogin()==true) {
	    		
	    		 expertBean.setPro_id(loginProuserBean.getPro_id());
	    	}
	        proProfileService.addExpertInfo(expertBean);
	        System.out.println("save");
	    }
	    
	    return "updated successfully!";
	}
	
	//가격 수정
	@PostMapping(value = "/expert_modify", consumes = "application/json")
	@ResponseBody
	public String expert_modify(@RequestBody ExpertBean modifyPriceBean) {
		
		proProfileService.modifyPrice(modifyPriceBean.getPrice(), loginProuserBean.getPro_id());
		
		return "updated successfully";
	}
	
	//아숨 활동명
	@PostMapping(value = "/expert_modifyName", consumes = "application/json")
	@ResponseBody
	public String expert_modifyName(@RequestBody ProUserBean modifyProUserBean) {
	    //System.out.println("수정된 활동명: " + modifyProUserBean.getPro_name());
	    proUserService.modifyProName(modifyProUserBean.getPro_name(), modifyProUserBean.getPro_id());
	    return "update name successfully";
	}
	
	//제공 서비스
	@PostMapping(value = "/expert_modifyCategory", consumes = "application/json")
	@ResponseBody
	public String expert_modifyCategory(@RequestBody CateProuserBean modifyCategoryBean) {
		
		// 클라이언트에서 전송한 선택된 카테고리 정보
	    //String active_detailcategory1 = modifyCategoryBean.getActive_detailcategory1();
	    //String active_detailcategory2 = modifyCategoryBean.getActive_detailcategory2();
	    //String active_detailcategory3 = modifyCategoryBean.getActive_detailcategory3();

	    // 기존에 저장되어 있던 카테고리 정보
	    //String cate1 = proUserService.getCategory1(loginProuserBean.getPro_id());
	    //String cate2 = proUserService.getCategory2(loginProuserBean.getPro_id());
	    //String cate3 = proUserService.getCategory3(loginProuserBean.getPro_id());

	    // 선택된 카테고리가 null이 아니면 업데이트, null이면 이전 값 그대로 사용
	   // modifyCategoryBean.setActive_detailcategory1(active_detailcategory1 != null ? active_detailcategory1 : cate1);
	   // modifyCategoryBean.setActive_detailcategory2(active_detailcategory2 != null ? active_detailcategory2 : cate2);
	    //modifyCategoryBean.setActive_detailcategory3(active_detailcategory3 != null ? active_detailcategory3 : cate3);
		 
	    if (modifyCategoryBean.getActive_detailcategory1() != null && !modifyCategoryBean.getActive_detailcategory1().isEmpty()) {
	        proUserService.modifyCategory(modifyCategoryBean.getActive_detailcategory1(),modifyCategoryBean.getPro_id());
	    }

	    if (modifyCategoryBean.getActive_detailcategory2() != null && !modifyCategoryBean.getActive_detailcategory2().isEmpty()) {
	        proUserService.modifyCategory2(modifyCategoryBean.getActive_detailcategory2(),modifyCategoryBean.getPro_id());
	    }

	    if (modifyCategoryBean.getActive_detailcategory3() != null && !modifyCategoryBean.getActive_detailcategory3().isEmpty()) {
	        proUserService.modifyCategory3(modifyCategoryBean.getActive_detailcategory3(),modifyCategoryBean.getPro_id());
	    }
	    System.out.println("카테고리 수정: " +
	            modifyCategoryBean.getActive_detailcategory1() + ", " +
	            modifyCategoryBean.getActive_detailcategory2() + ", " +
	            modifyCategoryBean.getActive_detailcategory3());
	    return "update category success";
	}
	
	//제공 서비스 삭제
	@PostMapping(value = "/expert_remove_category1", consumes = "application/json")
	@ResponseBody
	public String expert_remove_category1(@RequestBody ProUserBean deleteCategoryBean1) {
		
		proUserService.deleteCategory1(loginProuserBean.getPro_id());
		//proUserService.deleteCategory2(loginProuserBean.getPro_id());
		//proUserService.deleteCategory3(loginProuserBean.getPro_id());
		
		System.out.println("삭제 후 11: " + deleteCategoryBean1.getActive_detailcategory1());
		//System.out.println("삭제 후 22: " + deleteCategoryBean.getActive_detailcategory2());
		//System.out.println("삭제 후 33: " + deleteCategoryBean.getActive_detailcategory3());
		
		return "delete category111 success";
	}
	
	@PostMapping(value = "/expert_remove_category2", consumes = "application/json")
	@ResponseBody
	public String expert_remove_category2(@RequestBody ProUserBean deleteCategoryBean2) {
		
		//proUserService.deleteCategory1(loginProuserBean.getPro_id());
		proUserService.deleteCategory2(loginProuserBean.getPro_id());
		//proUserService.deleteCategory3(loginProuserBean.getPro_id());
		
		//System.out.println("삭제 후 11: " + deleteCategoryBean.getActive_detailcategory1());
		System.out.println("삭제 후 22: " + deleteCategoryBean2.getActive_detailcategory2());
		//System.out.println("삭제 후 33: " + deleteCategoryBean.getActive_detailcategory3());
		
		return "delete category222 success";
	}
	
	@PostMapping(value = "/expert_remove_category3", consumes = "application/json")
	@ResponseBody
	public String expert_remove_category3(@RequestBody ProUserBean deleteCategoryBean3) {
		
		//proUserService.deleteCategory1(loginProuserBean.getPro_id());
		//proUserService.deleteCategory2(loginProuserBean.getPro_id());
		proUserService.deleteCategory3(loginProuserBean.getPro_id());
		
		//System.out.println("삭제 후 11: " + deleteCategoryBean.getActive_detailcategory1());
		//System.out.println("삭제 후 22: " + deleteCategoryBean.getActive_detailcategory2());
		System.out.println("삭제 후 33: " + deleteCategoryBean3.getActive_detailcategory3());
		
		return "delete category333 success";
	}
	
	//일류 지역
   @PostMapping(value = "/expert_modify2", consumes = "application/json")
   @ResponseBody
   public String expert_modify2(@RequestBody ProUserBean modifyActive_locationBean) {
      
      proUserService.modifyActive_location(modifyActive_locationBean.getActive_location(), loginProuserBean.getPro_id());
      
      return "updated location successfully";
   }
	
	//자격증 이미지
   @PostMapping("/image_pro")
   public String image_pro(@ModelAttribute("imageExpertBean") ExpertBean imageExpertBean,
         @RequestParam("pro_id") int pro_id,
                     @RequestParam("uploadFiles") List<MultipartFile> uploadFiles, Model model) {
      //System.out.println("자격증 사진드으으을: "+imageExpertBean.getPro_profile_image());
      String pro_profile_image = imageExpertBean.getPro_profile_image();
      proProfileService.modifyImg(pro_profile_image, pro_id, uploadFiles);
      
      String imageInfo = proProfileService.getImageInfo(pro_id);
       model.addAttribute("imageInfo", imageInfo);
       
      return "redirect:/pro/expert?id=" + pro_id;
   }
   
   @PostMapping("/profileImg_pro")
   public String profileImg_pro(@ModelAttribute("profileImgExpertBean") ExpertBean profileImgExpertBean) {
   
      
      int pro_id = profileImgExpertBean.getPro_id();
      System.out.println(pro_id);
      proProfileService.modifyProfileImg(profileImgExpertBean);
      return "redirect:/pro/expert?id=" + pro_id;
   }
	
    //년, 월
	private void extractYearAndMonth(CareerBean career) {
	    // 시작 날짜 정보 가공
	    if (career.getStart_date() != null) {
	        Calendar startCal = Calendar.getInstance();
	        startCal.setTime(career.getStart_date());
	        career.setStartYear(startCal.get(Calendar.YEAR));
	        career.setStartMonth(startCal.get(Calendar.MONTH) + 1); // Calendar의 월은 0부터 시작하므로 +1 처리
	    }

	    // 종료 날짜 정보 가공
	    if (career.getEnd_date() != null) {
	        Calendar endCal = Calendar.getInstance();
	        endCal.setTime(career.getEnd_date());
	        career.setEndYear(endCal.get(Calendar.YEAR));
	        career.setEndMonth(endCal.get(Calendar.MONTH) + 1); // Calendar의 월은 0부터 시작하므로 +1 처리
	    }
	}
	
	private void extractYearAndMonth2(EducationBean education) {
	    // 시작 날짜 정보 가공
	    if (education.getAdmission_date() != null) {
	        Calendar startCal = Calendar.getInstance();
	        startCal.setTime(education.getAdmission_date());
	        education.setAdmissionYear(startCal.get(Calendar.YEAR));
	        education.setAdmissionMonth(startCal.get(Calendar.MONTH) + 1); // Calendar의 월은 0부터 시작하므로 +1 처리
	    }

	    // 종료 날짜 정보 가공
	    if (education.getGraduation_date() != null) {
	        Calendar endCal = Calendar.getInstance();
	        endCal.setTime(education.getGraduation_date());
	        education.setGraduationYear(endCal.get(Calendar.YEAR));
	        education.setGraduationMonth(endCal.get(Calendar.MONTH) + 1); // Calendar의 월은 0부터 시작하므로 +1 처리
	    }
	}
	
	@GetMapping("/career")
	public String career(Model model, @ModelAttribute("careerForm")CareerBean careerBean) {
		
		
		
		return "pro/career";
	}
	
	@PostMapping("/career_pro") 
	public String career_pro(@ModelAttribute("careerForm")CareerBean careerBean) {
		
		 if(loginProuserBean.isProuserLogin()==true) {

			  careerBean.setPro_id(loginProuserBean.getPro_id());
			  
		  } 
		// updateStartDate 메서드 호출
		    careerBean.updateStartDate();
		    careerBean.updateEndDate();

		
		careerService.addCareerInfo(careerBean);
		
		
		return "/pro/career_success";
	}
	
	@GetMapping("/career_modify")
	public String career_modify(@RequestParam("career_id") int career_id, @ModelAttribute("careerContentModifyBean") CareerBean careerContentModifyBean, Model model) {
		
		model.addAttribute("career_id", career_id);
		
		CareerBean tempCareerBean = careerService.getCareerInfo(career_id);
		
		careerContentModifyBean.setCareer_title(tempCareerBean.getCareer_title());
		careerContentModifyBean.setDetailed_introduction(tempCareerBean.getDetailed_introduction());
		careerContentModifyBean.setTotal_experience_period(tempCareerBean.getTotal_experience_period());
		//careerModifyBean.setStart_date(tempCareerBean.getStart_date());
		careerContentModifyBean.setCareer_id(career_id);
		
		
		return "/pro/career_modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@ModelAttribute("careerContentModifyBean") CareerBean careerContentModifyBean, Model model) {
		
		// updateStartDate 메서드 호출
		careerContentModifyBean.updateStartDate();
		careerContentModifyBean.updateEndDate();
		careerService.modifyCareer(careerContentModifyBean);
		
		return "/pro/modify_success";
	}
	
	@GetMapping("/career_delete")
	public String career_delete(@RequestParam("career_id") int career_id) {
		
		careerService.deleteCareer(career_id);
		
		return "/pro/career_delete";
	}
	
	//-----------------------------------------------------------------------학력
	
	@GetMapping("/Education")
	public String Education(@ModelAttribute("educationForm") EducationBean educationBean) {
		
		return "pro/Education";
	}
	
	@PostMapping("/Education_pro")
	public String Education_pro(@ModelAttribute("educationForm") EducationBean postEducationBean, Model model, @RequestParam("eduUpload") List<MultipartFile> eduUpload) {
		
		if(loginProuserBean.isProuserLogin()==true) {

			postEducationBean.setPro_id(loginProuserBean.getPro_id());
			  
		  } 
		postEducationBean.updateAdmissionDate();
		postEducationBean.updateGraduationDate();
		
		educationService.addEducationInfo(postEducationBean, eduUpload);
		
		return "pro/career_success";
	}
	
	@GetMapping("/education_modify")
	public String education_modify(@RequestParam("education_id") int education_id, Model model, @ModelAttribute("educationContentModifyBean") EducationBean educationContentModifyBean) {
		
		model.addAttribute("education_id", education_id);
		List<EducationBean> educationList = educationService.getEducationList(loginProuserBean.getPro_id());
		
		EducationBean tempEducation = educationService.getEducationInfo(education_id);
		
		educationContentModifyBean.setSchool_name(tempEducation.getSchool_name());
		educationContentModifyBean.setMajor_name(tempEducation.getMajor_name());
		educationContentModifyBean.setAdmissionYear(tempEducation.getAdmissionYear());
		educationContentModifyBean.setAdmissionMonth(tempEducation.getAdmissionMonth());
		educationContentModifyBean.setEvidence_image(tempEducation.getEvidence_image());
		educationContentModifyBean.setEducation_id(education_id);
		
		 // 연도 및 월을 모델에 추가
	    model.addAttribute("yearOptions", getYearOptions());
	    model.addAttribute("monthOptions", getMonthOptions());
	    model.addAttribute("educationList", educationList);
		
		return "/pro/education_modify";
	}
	
	@PostMapping("/education_modify_pro")
	public String education_modify_pro(@RequestParam("education_id") int education_id, 
										Model model, @ModelAttribute("educationContentModifyBean") EducationBean educationContentModifyBean, 
										@RequestParam("eduUpload") List<MultipartFile> eduUpload) {
		
		educationContentModifyBean.updateAdmissionDate();
		educationContentModifyBean.updateGraduationDate();
		educationService.modifyEducationInfo(educationContentModifyBean, eduUpload);
		
		
		return "pro/modify_success";
	}
	
	@GetMapping("/education_delete")
	public String education_delete(@RequestParam("education_id") int education_id) {
		
		educationService.deleteEducation(education_id);
		
		return "/pro/career_delete";
	}
	
	//--------------------------------------------------------------------------포트폴리오
	
	@GetMapping("/Portfolio")
    public String Portfolio(@ModelAttribute("Proportfolio_pro") PortFolioBean Proportfolio_pro, 
    										Model model) {
    	//model.addAttribute("ProPortpolio_pro",ProPortpolio_pro);
    	
    	 
    	model.addAttribute("Proportfolio_pro", Proportfolio_pro);
    	
        //return "pro/Portfolio";
    	return "pro/Portfolio";
    } 
    
    
    @PostMapping("/ProPortfolio")
    public String ProPortfolio(@ModelAttribute("Proportfolio_pro") PortFolioBean Proportfolio_pro, Model model
    		,@RequestParam("uploadFiles") List<MultipartFile> uploadFiles) {
     
    	  	portfolioService.addProPortfolioInfo(Proportfolio_pro, uploadFiles);
		   model.addAttribute("Proportfolio_pro", Proportfolio_pro);
    	
    	return "pro/portfolio_success";
    }
    
    
    @GetMapping("/Portfolio_modify")
	public String Portfolio_modify(@RequestParam("portfolio_id") int portfolio_id, @ModelAttribute("ProportfolioModify") PortFolioBean ProportfolioModify,
							Model model) {
		
    	PortFolioBean PortfolioIdList = portfolioService.getPortfolioIdList(portfolio_id);

    	System.out.println("PortfolioIdList.getDetailed_introduction: "+PortfolioIdList.getDetailed_introduction() );
    	System.out.println("PortfolioIdList.getDetailed_images: "+PortfolioIdList.getDetailed_images() );
    	System.out.println("PortfolioIdList.getLocation_info: "+PortfolioIdList.getLocation_info() );
    	
		model.addAttribute("PortfolioIdList",PortfolioIdList);
		
		return "pro/Portfolio_modify";
		
	}
    
	@PostMapping("/ModifyProPortfolio")
	public String ModifyProPortfolio(@Valid @ModelAttribute("ProportfolioModify") PortFolioBean ProportfolioModify,
								@RequestParam("uploadFiles") List<MultipartFile> uploadFiles,
								 BindingResult result) {
		
		if(result.hasErrors()) {
			return "pro/Portfolio_modify";
		}
		
		try { 
			portfolioService.modifyPortfolioInfo(ProportfolioModify, uploadFiles);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
		return "pro/ModifyProPortfolio_success";
	}
}