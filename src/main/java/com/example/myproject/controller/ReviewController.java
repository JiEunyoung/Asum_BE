package com.example.myproject.controller;

import java.util.Calendar;
import java.util.List;

import com.example.myproject.model.*;
import com.example.myproject.service.InterestService;
import com.example.myproject.service.ProUserService;
import com.example.myproject.service.ReviewService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private InterestService interestService;
	
	@Autowired
	private ProUserService proUserService;
	
	@Resource(name="userBean")
	private UserBean loginUserBean;

	@GetMapping("/ReviewWrite")
	public String reviewWrite(@ModelAttribute("writeReviewBean") ReviewBean writeReviewBean, Model model,
							  @RequestParam(name="pro_id") int pro_id) {
		
		//@RequestParam(name="pro_id") int pro_id 추가
		
		//(프로_프로필)서비스 서비스 = new (프로_프로필)서비스;
		//(프로_프로필)빈 빈 = 서비스.getPro_id(pro_id);
		//model.addAttribute("빈", 빈);
		//jsp에서 ${빈.필요한 데이터} 형태
		
		ProUserBean proUserBean = reviewService.getProReviewInfo(pro_id);
		String ctg1 = proUserService.getCategory11(pro_id);
		String ctg2 = proUserService.getCategory22(pro_id);
		String ctg3 = proUserService.getCategory33(pro_id);
		
		model.addAttribute("proUserBean", proUserBean);
		System.out.println("proUserBean :"+proUserBean.getPro_name());
		model.addAttribute("ctg1", ctg1);
		model.addAttribute("ctg2", ctg2);
		model.addAttribute("ctg3", ctg3);
		model.addAttribute("writeReviewBean", writeReviewBean);
		
		return "review/ReviewWrite";
	}
	
	@PostMapping("/ReviewWrite_pro")
	public String reviewWrite_pro(@Valid @ModelAttribute("writeReviewBean") ReviewBean writeReviewBean, BindingResult result,
								  @RequestParam("uploadFiles") List<MultipartFile> uploadFiles) {
		
		if(result.hasErrors()) {
			return "review/ReviewWrite";
		}
		
		reviewService.addReviewInfo(writeReviewBean, uploadFiles);
		
		return "review/ReviewWrite_success";
	}
	
	//년, 월 경력
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
	
	//학력
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
	
	@GetMapping("/Review")
	public String Review(@RequestParam("pro_id") int pro_id, Model model) {
		
		List<ReviewBean> reviewList = reviewService.getAllReviewList(pro_id);
		
		int reviewCnt = reviewService.getReviewCnt(pro_id);
		
		float reviewAvg = reviewService.getAvgReview(pro_id);
		
		ProProfileBean proProfileBean = reviewService.getProProfileInfo(pro_id);
		
		List<CareerBean> careerList = reviewService.getCareerListInfo(pro_id);
		
		Integer sumTotal = reviewService.getSumTotalExperience(pro_id);
		
		List<EducationBean> educationList = reviewService.getEducationListInfo(pro_id);
		
		//커리어, 학력 년,월
		careerList.forEach(this::extractYearAndMonth);
		
		educationList.forEach(this::extractYearAndMonth2);
		
		//PortFolioBean tempPortfolioBean = reviewService.getPortfolioInfo(portfolio_id)
		
		List<PortFolioBean> portfolioList = reviewService.getProtfolioListInfo(pro_id);
		
		//PortFolioBean readPortfolioBean = reviewService.getPortfolioInfo(portfolio_id);
		
		
		
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("reviewAvg", reviewAvg);
		model.addAttribute("loginUserBean", loginUserBean);
		model.addAttribute("proProfileBean", proProfileBean);
		model.addAttribute("careerList", careerList);
		model.addAttribute("sumTotal", sumTotal);
		model.addAttribute("educationList", educationList);
		model.addAttribute("portfolioList", portfolioList);
		
		return "review/Review";
	}
	
	 @PostMapping(value="/deleteReview", consumes="application/json")
	 @ResponseBody
	 public void deleteReview(@RequestBody ReviewBean reviewBean) {
	    reviewService.deleteReview(reviewBean.getReview_id());
	 }
	
	@PostMapping("/toggleInterest")
	@ResponseBody
	public ResponseEntity<String> toggleInterest(
            @RequestParam("pro_id") int pro_id) 
	{
        int user_id = loginUserBean.getUser_id();
        interestService.toggleInterest(user_id, pro_id);
        return ResponseEntity.ok("Liked toggled successfully");
    }
}