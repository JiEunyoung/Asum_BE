package com.example.myproject.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.myproject.model.DetailCategoryBean;
import com.example.myproject.model.ProUserBean;
import com.example.myproject.model.UserBean;
import com.example.myproject.service.DetailCategoryService;
import com.example.myproject.service.ProUserService;
import com.example.myproject.service.ServiceCategoryService;
import com.example.myproject.service.UserService;
import com.example.myproject.validator.UserValidator;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private ProUserService ProuserService;

	@Resource(name = "userBean")
	private UserBean loginUserBean;

	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;

	@Autowired
	ServiceCategoryService serviceCategoryService;

	@Autowired
	DetailCategoryService detailCategoryService;

	
	// 일반 회원 로그인
	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			@RequestParam(value = "fail", defaultValue = "false") boolean fail, Model model) {

		model.addAttribute("fail", fail);

		return "user/login";
	}

	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			BindingResult result) {

		if (result.hasErrors()) {
			return "user/login";
		}
		try {
			userService.getLoginUserInfo(tempLoginUserBean);
			System.out.println("userController-getUser_name:" + tempLoginUserBean.getUser_name());
			if (loginUserBean.isUserLogin() == true) {

				return "user/login_succes";
			
			} else {
				
				return "user/login_fail";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			// NullPointerException이 발생하면 로그인 실패로 처리
			return "user/login_fail";
		}

	}

	// 고수 로그인
	@GetMapping("/pro_login")
	public String pro_login(@ModelAttribute("tempLoginUserBean2") ProUserBean tempLoginUserBean2,
			@RequestParam(value = "fail", defaultValue = "false") boolean fail, Model model) {

		model.addAttribute("fail", fail);
		return "user/pro_login";
	}

	@PostMapping("/proUser_login")
	public String pro_Login(@Valid @ModelAttribute("tempLoginUserBean2") ProUserBean tempLoginUserBean2,
			BindingResult result) {

		if (result.hasErrors()) {

			return "user/pro_login";
		}
		try {
			ProuserService.getLoginProuserInfo(tempLoginUserBean2);
			System.out.println("userBeanController:" + tempLoginUserBean2.getPro_name());
			System.out.println("loginUserBean : " + loginProuserBean.getPro_name());
			if (loginProuserBean.isProuserLogin() == true) {

				return "user/login_succes";

			} else {

				return "user/pro_login_fail";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			// NullPointerException이 발생하면 로그인 실패로 처리
			return "user/pro_login_fail";
		}
	}

	@GetMapping("/not_login")
	public String not_login() {

		return "user/not_login";
	}

	@GetMapping("/logout")
	public String logout() {

		loginUserBean.setUserLogin(false);

		return "user/logout";
	}

	@GetMapping("/pro_logout")
	public String pro_logout() {

		loginProuserBean.setProuserLogin(false);

		return "user/pro_logout";
	}

	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {

		return "user/join";
	}

	@PostMapping("/join_user")
	public String join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {
		if (result.hasErrors()) {
			return "user/join";
		}
		userService.addUserInfo(joinUserBean);

		return "user/join_success";
	}

	// 일류가입
	@GetMapping("/pro_join")
	public String pro_join(@ModelAttribute("joinProuserBean") ProUserBean joinProuserBean, Model model) {
		ArrayList<List<DetailCategoryBean>> detailCategoryList = new ArrayList<>();
		ArrayList<String> service_Category_Name = new ArrayList<>();

		for (int i = 1; i <= 8; i++) {
			List<DetailCategoryBean> list1 = detailCategoryService.getDetailCategoryList(i);
			String categoryName = detailCategoryService.getServiceCategoryName(i);
			detailCategoryList.add(list1);
			service_Category_Name.add(categoryName);
		}
		model.addAttribute("detailCategoryList", detailCategoryList);
		model.addAttribute("service_category_name", service_Category_Name);

		return "user/pro_join";
	}

	// 일류가입
	@PostMapping("/join_Prouser")
	public String join_Prouser(@Valid @ModelAttribute("joinProuserBean") ProUserBean joinProuserBean,
			BindingResult result) {
		if (result.hasErrors()) {
			return "user/pro_join";
		}
		ProuserService.addProuserInfo(joinProuserBean);

		return "user/pro_join_success";
	}

	@InitBinder
	public void initBinder(WebDataBinder blinder) {

		UserValidator validator1 = new UserValidator();
		blinder.addValidators(validator1);
	}

}