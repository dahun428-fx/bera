package com.example.demo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.UserService;
import com.example.demo.vo.User;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view/user/login");
		return mav;
	}
	@GetMapping("/join")
	public ModelAndView joinForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view/user/join");
		return mav;
	}
	
	/**
	 * REST 로부터 user 객체를 받아와서, 회원가입 구현
	 * @param user
	 * @return type, msg type = error, success 
	 */
	@PostMapping("/join")
	@ResponseBody
	public Map<String, Object> join(@RequestBody User user) {
		return userService.join(user);
	}

}
