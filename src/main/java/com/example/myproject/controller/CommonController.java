package com.example.myproject.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common")
public class CommonController {
	@Resource(name = "userBean")
	private UserBean loginUserBean;
	
	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ProUserService prouserService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	CalendarService calendarService;
	
	@Autowired
	private InterestService interestService;
	 
	@Autowired
	ProProfileService proProfileService;
	
	@GetMapping("/myPage")
	public String mypage(Model model) { 
		
		//userService.getLoginUserInfo(tempLoginUserBean);
		//System.out.println("tempLoginUserBean: " + tempLoginUserBean.getUser_name());
		//System.out.println("tempLoginUserBean: " + tempLoginUserBean.getUser_name());
		if (loginUserBean.isUserLogin()) {
	        // 일반 회원으로 로그인한 경우
	      
	        model.addAttribute("user_name", loginUserBean.getUser_name());
	        model.addAttribute("user_email", loginUserBean.getUser_email());
	        
	        System.out.println("user_namegg: " + loginUserBean.getUser_name());
	        System.out.println("user_emailgg: " + loginUserBean.getUser_email());
	    } else if (loginProuserBean.isProuserLogin()) {
	    	String profileImgInfo = proProfileService.getProfileImgInfo(loginProuserBean.getPro_id());
	           model.addAttribute("profileImgInfo", profileImgInfo);
	        model.addAttribute("pro_name", loginProuserBean.getPro_name());
	        model.addAttribute("pro_email", loginProuserBean.getPro_email());
	        System.out.println("proname: " + loginProuserBean.getPro_name());
	    } else {
	        // 로그인되지 않은 경우
	        return "redirect:/user/not_login";
	    }

		return "common/myPage";
	}

	@GetMapping("/AccountSetting")
	public String AccountSetting(@ModelAttribute("AccountModifyCom") UserBean AccountModifyCom
								,@ModelAttribute("ProAccountModifyCom") ProUserBean ProAccountModifyCom					 
								,Model model) {
		
		AccountModifyCom.setUser_id(loginUserBean.getUser_id());
		
		
		if(loginUserBean.isUserLogin()==true) {
			AccountModifyCom.setUser_name(loginUserBean.getUser_name());
			AccountModifyCom.setUser_email(loginUserBean.getUser_email());
			AccountModifyCom.setUser_pwd(loginUserBean.getUser_pwd());

			
		}else if(loginProuserBean.isProuserLogin()==true) {
			
			ProAccountModifyCom.setPro_name(loginProuserBean.getPro_name());
			ProAccountModifyCom.setPro_email(loginProuserBean.getPro_email());
			ProAccountModifyCom.setPro_pwd(loginProuserBean.getPro_pwd());
		}

		model.addAttribute("AccountModifyCom", AccountModifyCom);
		model.addAttribute("ProAccountModifyCom", ProAccountModifyCom);
		return "common/AccountSetting";	
	}
	
	@GetMapping("/AccountModify")
	public String AccountModify(Model model,
								@ModelAttribute("AccountModifyCom") UserBean AccountModifyCom
								,@ModelAttribute("ProAccountModifyCom") ProUserBean ProAccountModifyCom) {
		
		if(loginUserBean.isUserLogin()==true) {
			
			AccountModifyCom.setUser_name(loginUserBean.getUser_name());
			AccountModifyCom.setUser_email(loginUserBean.getUser_email());
			AccountModifyCom.setUser_pwd(loginUserBean.getUser_pwd());
			System.out.println("===========================");
			System.out.println(AccountModifyCom.getUser_id());
			System.out.println("==========================");
			
		}else if(loginProuserBean.isProuserLogin()==true) {
			
			ProAccountModifyCom.setPro_name(loginProuserBean.getPro_name());
			ProAccountModifyCom.setPro_email(loginProuserBean.getPro_email());
			ProAccountModifyCom.setPro_pwd(loginProuserBean.getPro_pwd());
			
		} 
		model.addAttribute("AccountModifyCom", AccountModifyCom);
		model.addAttribute("ProAccountModifyCom", ProAccountModifyCom);
		
	 
		return "common/AccountModify";
	}
	
	//일반 사용자 계정 수정 
	@PostMapping("/AccountModify_user")
	public String AccountModify_pro(@Valid @ModelAttribute("AccountModifyCom") UserBean AccountModifyCom
									, BindingResult result, Model model ) {
		
		System.out.println("3333333333333333333333333333");
		AccountModifyCom.setUser_id(loginUserBean.getUser_id());
		System.out.println(AccountModifyCom.getUser_id());
		
		if(result.hasErrors()) {
			return "common/AccountModify";
		} 
		
		userService.updateAccountUser(AccountModifyCom);
		
		
		return "common/AccountModify_succes";
		
		
	}
	
	//일류 계정 수정 
	@PostMapping("/AccountModify_Pro")
	public String AccountModify_Pro(@Valid @ModelAttribute("ProAccountModifyCom") ProUserBean ProAccountModifyCom
			,BindingResult result, Model model ) {
		
		System.out.println("3333333333333333333333333333");
		ProAccountModifyCom.setPro_id(loginProuserBean.getPro_id());
		System.out.println(ProAccountModifyCom.getPro_id());
		
		if(result.hasErrors()) {
			return "common/AccountModify";
		} 
		
		prouserService.ProupdateAccountUser(ProAccountModifyCom);
	 
		return "common/AccountModify_succes";
		 
	}
	
	@GetMapping("/AccountSecession")
	public String AccountSecession(@RequestParam("id") int id) {
		prouserService.ProAccountResign(id);
		return "user/pro_join";
	}
	
	//------------------------------------------캘린더
	
	@GetMapping("/calendar")
	public String calender(Model model) {
		
		//List<CalendarBean> calendarList = calendarService.getCalendarList(loginUserBean.getUser_id(),loginProuserBean.getPro_id());
		//model.addAttribute("calendarList", calendarList);
		
		System.out.println("login: " + loginUserBean.isUserLogin());
		System.out.println("pro_login: "+ loginProuserBean.isProuserLogin());
		//model.addAttribute("loginUserBean", loginUserBean);
		//model.addAttribute("ProUserBean", loginProuserBean);
		
		if (loginUserBean.isUserLogin()) {
	        // 일반 회원으로 로그인한 경우
	        List<CalendarBean> calendarList = calendarService.getCalendarList(loginUserBean.getUser_id(), null);
	        model.addAttribute("calendarList", calendarList);
	        model.addAttribute("userId", loginUserBean.getUser_id());
	    } else if (loginProuserBean.isProuserLogin()) {
	        // 일류로 로그인한 경우
	        List<CalendarBean> calendarList = calendarService.getCalendarList(null, loginProuserBean.getPro_id());
	        model.addAttribute("calendarList", calendarList);
	        model.addAttribute("proUserId", loginProuserBean.getPro_id());
	    } else {
	        // 로그인되지 않은 경우
	        return "redirect:/user/not_login";
	    }
		
		return "common/calendar";
	}
	
	@PostMapping("/calendar_pro")
	public String calendar_pro(@ModelAttribute("writeCalendarBean") CalendarBean writeCalendarBean) {
		
		//CalendarBean calendarBean = new CalendarBean();
		// ... 다른 필드 설정
		if (loginUserBean.getUser_id() == 0) {
		    // 일반 회원인 경우
			writeCalendarBean.setUser_id(null);
		} else if(loginProuserBean.getPro_id() == 0) {
			writeCalendarBean.setPro_id(null);
		}
		
		//if (loginUserBean.getUser_id() == 0) {
	        // 일반 회원인 경우
	    //    writeCalendarBean.setUser_id(null);
	    //    writeCalendarBean.setPro_id(loginProuserBean.getPro_id()); // 추가: 일반 회원일 경우 pro_id 설정
	   // } else {
	        // 고수인 경우
	    //    writeCalendarBean.setUser_id(loginUserBean.getUser_id()); // 추가: 고수일 경우 user_id 설정
	    //    writeCalendarBean.setPro_id(null);
	    //}
		
		System.out.println("login2: " + loginUserBean.isUserLogin());
		System.out.println("pro_login2: "+ loginProuserBean.isProuserLogin());

		calendarService.addCalendarInfo(writeCalendarBean);

	    return "common/write_success";
	}
	
	//@PostMapping("/calendar_modify/{calendar_Id}")
	//public String calendar_modify(@PathVariable int calendar_Id, @ModelAttribute("modifyCalendarBean") CalendarBean modifyCalendarBean, Model model) {
		
		//int calendar_id = modifyCalendarBean.getCalendar_Id();
		//CalendarBean existingCalendar = calendarService.getCalendarContent(calendar_Id);
		
		//modifyCalendarBean.setCalendar_memo(temp.getCalendar_memo());
		//modifyCalendarBean.setStart_date(temp.getStart_date());
		//modifyCalendarBean.setEnd_date(temp.getEnd_date());
		
		// 기존 캘린더 정보 가져오기
	    //CalendarBean existingCalendar = calendarService.getCalendarContent(calendar_Id);
	    
	    // 수정된 메모와 날짜 정보 설정
	   // existingCalendar.setCalendar_memo(modifyCalendarBean.getCalendar_memo());
	   // existingCalendar.setStart_date(modifyCalendarBean.getStart_date());
	   // existingCalendar.setEnd_date(modifyCalendarBean.getEnd_date());
	    
	    // 캘린더 업데이트
	    //calendarService.modifyCalendar(existingCalendar);
	    
		
		//return "mypage/calendar";
	//}
	
	@PostMapping("/calendar_modify/{calendar_Id}")
    @ResponseBody
    public String modifyCalendarEvent(
    		@PathVariable("calendar_Id") int calendar_Id,
            @RequestParam("start_date") String start_date,
            @RequestParam("end_date") String end_date,
            @RequestParam("calendar_memo") String calendar_memo,
             @ModelAttribute("modifyCalendarBean") CalendarBean modifyCalendarBean, Model model) {

        // 여기에 이벤트 수정 로직을 추가할 수 있습니다.
        // eventId, startDate, endDate, calendarMemo를 이용하여 적절한 업데이트를 수행합니다.
		
		// 기존 캘린더 정보 가져오기
	    //CalendarBean existingCalendar = calendarService.getCalendarContent(calendar_Id);
	    
	    // 수정된 메모와 날짜 정보 설정
	    //existingCalendar.setCalendar_memo(modifyCalendarBean.getCalendar_memo());
	    //existingCalendar.setStart_date(modifyCalendarBean.getStart_date());
	    //existingCalendar.setEnd_date(modifyCalendarBean.getEnd_date());
	    
	    // 수정된 정보를 데이터베이스에 반영
	    calendarService.modifyCalendar(modifyCalendarBean);

        // 예시로 간단하게 로그를 찍어봅니다.
        System.out.println("Received updated event info:");
        System.out.println("Start Date: " + start_date);
        System.out.println("End Date: " + end_date);
        System.out.println("Calendar Memo: " + calendar_memo);
        System.out.println("calendar id: " + calendar_Id);

        // 성공 응답 반환
        return "common/calendar";
        
    }
	
	@PostMapping("/calendar_delete/{calendar_Id}")
	@ResponseBody
	public String calendar_delete(@PathVariable("calendar_Id") int calendar_Id) {
		
		 String result;
	        try {
	            calendarService.delecteCalendar(calendar_Id);
	            result = "Event deleted successfully!";
	        } catch (Exception e) {
	            result = "Error deleting event: " + e.getMessage();
	        }
		
		
		return result;
	}
	
	//------------------------------------------- 내가 쓴 글들
	@GetMapping("/myPosts")
	public String myPosts(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		
		if(loginUserBean.isUserLogin() == true) {
			List<PostBean> getMyPosts = postService.getMyPosts(loginUserBean.getUser_id(), null, page);
			model.addAttribute("getMyPosts", getMyPosts);
			PageBean pageBean = postService.getMyPostsPage(page, loginUserBean.getUser_id(), null);
			model.addAttribute("pageBean", pageBean);
			
		} else if(loginProuserBean.isProuserLogin() == true) {
			List<PostBean> getMyPosts = postService.getMyPosts(null, loginProuserBean.getPro_id(), page);
			model.addAttribute("getMyPosts", getMyPosts);
			PageBean pageBean = postService.getMyPostsPage(page, null, loginProuserBean.getPro_id());
			model.addAttribute("pageBean", pageBean);
		}else {
	        // 로그인되지 않은 경우
	        return "redirect:/user/not_login";
	    }
		
		return "common/myPosts";
	}
	
	//--------------------------------------------------------내가 쓴 댓글
	
	@GetMapping("/comment")
	public String comment(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		
		if(loginUserBean.isUserLogin() == true) {
			List<CommentBean> getMyComments = postService.getMyComment(loginUserBean.getUser_id(), null, page);
			model.addAttribute("getMyComments", getMyComments);
			PageBean pageBean = postService.getMyCommentPage(page, loginUserBean.getUser_id(), null);
			model.addAttribute("pageBean", pageBean);
			
			 for (CommentBean comment : getMyComments) {
			        //System.out.println("Comment ID: " + comment.getComments_id());
			        System.out.println("Board ID: " + comment.getBoard_id());
			        //System.out.println("User ID: " + comment.getUser_id());
			        //System.out.println("Pro ID: " + comment.getPro_id());
			        //System.out.println("Comment Content: " + comment.getCommentContent());
			        //System.out.println("Comments Date: " + comment.getComments_date());
			        //System.out.println("title: " + comment.getTitle());
			        System.out.println("------------------------------------");
			    }
		
		} else if(loginProuserBean.isProuserLogin() == true) {
			
			List<CommentBean> getMyComments = postService.getMyComment(null, loginProuserBean.getPro_id(), page);
			model.addAttribute("getMyComments", getMyComments);
			PageBean pageBean = postService.getMyCommentPage(page, null, loginProuserBean.getPro_id());
			model.addAttribute("pageBean", pageBean);
			
			System.out.println("page: " + pageBean.getMax());
			
		}else {
	        // 로그인되지 않은 경우
	        return "redirect:/user/not_login";
	    }
		
		return "common/comment";
	}
	//--------------------------------------------------------------내가 찜한 일류
	
		@GetMapping("/saved")
		public String saved(Model model, @RequestParam("id") int id) {
			
			//이름, 프로필 사진, 상세 소개, pro_id
			List<InterestBean> getProfileList = interestService.getProfileList(id);//프로필
			System.out.println("id...: " + id);
			model.addAttribute("getProfileList", getProfileList);
			
			// for 루프를 사용하여 리스트의 각 요소를 출력
			for (InterestBean interest : getProfileList) {
			    System.out.println("pro_id: " + interest.getPro_id());
			    System.out.println("pro_detailed_introduction: " + interest.getPro_detailed_introduction());
			    System.out.println("certification_documents_images: " + interest.getCertification_documents_images());
			    System.out.println("pro_name: " + interest.getPro_name());
			    System.out.println("---------------------------");
			}
			
			
			//경력
			List<InterestBean> getcareerList = interestService.getcareerList(id);
			model.addAttribute("getcareerList", getcareerList);
			
			for(InterestBean interest2 : getcareerList) {
				System.out.println("pro_id : " + interest2.getPro_id());
				System.out.println("total_experience_period: " + interest2.getTotal_experience_sum());
				System.out.println("-----------------------------");
			}
			
			//리뷰
			List<InterestBean> getReviewList = interestService.getReviewList(id);
			model.addAttribute("getReviewList", getReviewList);
			
			for(InterestBean interest3 : getReviewList) {
				System.out.println("pro_iddddd: " + interest3.getPro_id());
				System.out.println("review_count: " + interest3.getReview_count());
				 
				System.out.println("--------------------------------");
			}
			
		
			return "common/saved";
		}
		
		//찜한 일류 삭제
		@PostMapping(value = "/interest_delete", consumes = "application/json")
		@ResponseBody
		public String interest_delete(@RequestBody InterestBean interestDeleteBean) {
			
			System.out.println("interest_idddddddd : " + interestDeleteBean.getInterest_id());
			interestService.InterestDelete(interestDeleteBean.getInterest_id());
			
			
			
			return "delete interest success";
		}
}
