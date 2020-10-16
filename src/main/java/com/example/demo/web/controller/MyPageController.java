package com.example.demo.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.UserService;
import com.example.demo.vo.User;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/cart")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("view/mypage/cart");
		return mav;
	}
	@GetMapping("/info")
	@ResponseBody
	public Map<String, Object> getInfo(){
		
		Map<String, Object> resultMap = new HashMap<>();
		User savedUser = userService.getLoginedUser();
		
		
		resultMap.put("userInfo", savedUser);
		
		return resultMap;
	}
}
