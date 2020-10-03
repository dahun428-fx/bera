package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/login")
	public String loginForm() {
		return "view/user/login";
	}
	@GetMapping("/join")
	public String joinForm() {
		return "view/user/join";
	}
	@GetMapping("/icecreams")
	public String productList() {
		return "view/product/list";
	}
	
	@GetMapping("/admin/addForm")
	public String productAddForm() {
		return "/view/product/addForm";
	}
	
	
}
