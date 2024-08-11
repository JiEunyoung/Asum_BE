package com.example.myproject.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.myproject.model.*;
import com.example.myproject.service.ChatService;
import com.example.myproject.service.ProUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionsCotroller {
	
	@Autowired
	ProUserService proUserService;
	
	QuestionBean questionBean;
	  
	@Resource(name = "userBean")
	private UserBean loginUserBean;

	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;
    
    @Autowired
    private ChatService chatService;
	
    
	@GetMapping("/received_quotation")
	public String received_quotation(@RequestParam("reco") String reco, Model model) {
	    List<String> recoProUsers = proUserService.getRecoProUserByName(reco);
	    System.out.println("recoProUsers: " + recoProUsers);
	    System.out.println("recoooooooooooo: " + reco);
	    model.addAttribute("recoProUsers", recoProUsers);
	    
	    List<Long> proIds = new ArrayList<>();
	    for (String recoProUser : recoProUsers) {
	        ProUserBean proUser = proUserService.getDetailCategoriesByName(recoProUser);
	        proIds.add(Long.valueOf(proUser.getPro_id())); 
		    System.out.println("proUser:"+proUser.getPro_id());
		    
	    }
	    model.addAttribute("proIds", proIds);

	 // 각 proId에 대한 카테고리 조회 및 모델에 추가
	    List<String> cate1List = new ArrayList<>();
	    List<String> cate2List = new ArrayList<>();
	    List<String> cate3List = new ArrayList<>();

	    for (Long proId : proIds) {
	        String cate1 = proUserService.getCategory1(proId);
	        String cate2 = proUserService.getCategory2(proId);
	        String cate3 = proUserService.getCategory3(proId);
	        System.out.println("proId :"+proId);
	        System.out.println("cate1 : " + cate1);
	        System.out.println("cate2 : " + cate2);
	        System.out.println("cate3 : " + cate3);
	        
	        // 리스트에 카테고리 추가
	        cate1List.add(cate1);
	        cate2List.add(cate2);
	        cate3List.add(cate3);
	    } 
	 // 모델에 리스트 추가
	    model.addAttribute("cate1List", cate1List);
	    model.addAttribute("cate2List", cate2List);
	    model.addAttribute("cate3List", cate3List);
	    
	    return "received_quotation";
	}
	
	@GetMapping("/ChattingList")
	public String chattingList(Model model) {
	    Integer userId = null;
	    Integer proId = null;
	    
	    // 일반 사용자로 로그인한 경우
	    if (loginUserBean.isUserLogin()) {
	        userId = loginUserBean.getUser_id();
	        List<ChatRoomSelect> chatrooms = chatService.findChatroomsWithProNameByUserId(userId);
	        model.addAttribute("chatrooms", chatrooms);
	    }
	    
	    // 전문가로 로그인한 경우
	    if (loginProuserBean.isProuserLogin()) {
	        proId = loginProuserBean.getPro_id();
	        userId = loginUserBean.getUser_id();
	        List<ChatRoomSelect> chatrooms = chatService.findChatroomsWithUserNameByProId(proId);
	        model.addAttribute("chatrooms", chatrooms);
	    }
	    
	    // 로그인하지 않은 경우 처리
	    if (userId == null && proId == null) {
	        return "redirect:/index";
	    }
	    
	    return "ChattingList";
	}
	  
	/* @RequestParam("pro_name") int proName */
	/* @RequestParam("s") int serviceCategoryId */
	 @GetMapping("/chatting")
	 public String chatting(Model model, 
			 @RequestParam("pro_id") int proId,// 프로의 ID를 요청 파라미터로 받음
			 @RequestParam(value = "s", required = false) Integer serviceCategoryId
	         ) {
	     
	     int userId; // 사용자 ID 초기화
	     if (loginUserBean.isUserLogin()) {
	         userId = loginUserBean.getUser_id(); // 일반 사용자로 로그인한 경우
	     } else if (loginProuserBean.isProuserLogin()) {
	         userId = loginProuserBean.getPro_id(); // 전문가로 로그인한 경우
	     } else {
	         // 로그인하지 않은 경우, 로그인 페이지로 리다이렉트하거나 오류 메시지 표시
	         return "redirect:/index"; // 예시: 로그인 페이지 경로로 리다이렉트
	     }

	  // 채팅방 생성 또는 기존 채팅방 조회
	     Integer existingRoomId = chatService.findChatroomIdByProIdAndUserId(proId, userId);
	     Integer existingRoomId2 = chatService.findChatroomIdByProId(proId);
	     if(loginUserBean.isUserLogin()) { 
		     if (existingRoomId == null) {
		         // 새 채팅방 생성
		    		 
		         ChatRoomBean chatroom = new ChatRoomBean();
		         chatroom.setPro_id(proId);
		         chatroom.setUser_id(userId);
		         //chatroom.setRoom_id(chatroom.getRoom_id());
		         
		         chatService.createChatroom(chatroom);
		         
    
		         
		     } else {
		         // 기존 채팅방 ID 사용
		         model.addAttribute("roomId", existingRoomId);
	
		     }
	     }
	     
	     if(loginProuserBean.isProuserLogin()) {
	
	    	 
	    	 model.addAttribute("roomId" , existingRoomId2);
	    	  
	     }
	     
	      model.addAttribute("currentUserId", userId); 
 
	      model.addAttribute("1", questionBean.getCertifaction_exam()); //자격증시험
	      model.addAttribute("2", questionBean.getInterior());  //인테리어
	      model.addAttribute("3", questionBean.getAppliance()); //가전제품
	      model.addAttribute("4", questionBean.getCleaning());  //청소
	      model.addAttribute("5", questionBean.getTranslation());  //번역
	      model.addAttribute("6", questionBean.getDocument()); //문서
	      model.addAttribute("7", questionBean.getDevelopment()); //개발외주
	      model.addAttribute("8", questionBean.getPet()); //반려동
 

	     return "chatting";
	 }
	 
	 	@PostMapping(value = "/sendQuote", consumes = "application/json")
	    @ResponseBody
	    public String sendQuote(@RequestBody QuoteBean quoteBean) {
	 		
	 		//System.out.println("Selected Answers: " + quoteBean.getReceived_quote());
	 	    System.out.println("Pro ID: " + quoteBean.getPro_id());
	 	    System.out.println("User ID: " + quoteBean.getUser_id());
	 	    chatService.insertReceiverQuote(quoteBean);
	
	        return "success";
	    }
 
		   @PostMapping("/deleteQuoteeee")
		    public ModelAndView deleteQuoteeee(@RequestParam("quote_history_id") int quote_history_id) {
		        ModelAndView modelAndView = new ModelAndView();
		        
		        chatService.delQuote(quote_history_id);
		        // 삭제 후, 관리자 페이지로 이동
		        modelAndView.setViewName("redirect:/include/header");
		
		        return modelAndView;
		
		  }
	 /*	
	 	@PostMapping("/deleteQuoteeee")
			public ResponseEntity<?> Completeportfolio_Delpro(@RequestParam("quote_history_id") int quote_history_id) {
			    try {
			        chatService.delQuote(quote_history_id);
			        return ResponseEntity.ok().build();
			    } catch (Exception e) {
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the quote.");
			    }
			}
	 	
	 	

	 	@PostMapping("/deleteQuoteeee")
	 	public void Completeportfolio_Delpro(@RequestParam("quote_history_id") int quote_history_id, HttpServletResponse response) {
	 	    chatService.delQuote(quote_history_id);
	 	    response.setStatus(HttpServletResponse.SC_OK);
	 	}*/
}