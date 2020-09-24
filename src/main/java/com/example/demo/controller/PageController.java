package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("user/login")
	public String loginForm() {
		return "view/user/login";
	}
	
	
}
