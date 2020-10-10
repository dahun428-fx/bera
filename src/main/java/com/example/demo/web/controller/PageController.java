package com.example.demo.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.vo.User;


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
	@GetMapping("/main")
	public String mainList(Model model) {
		model.addAttribute("uriType", "main");
		return "view/product/list";
	}
	@GetMapping("/icecream")
	public String icecreamList(Model model) {
		model.addAttribute("uriType", "icecream");
		return "view/product/list";
	}
	@GetMapping("/cake")
	public String cakeList(Model model) {
		model.addAttribute("uriType", "cake");
		return "view/product/list";
	}
	@GetMapping("/menu/{no}")
	public String productDetail(@PathVariable("no") int productNo, Model model) {
		model.addAttribute("productNo", productNo);
		return "view/product/detail";
	}
	@GetMapping("/order/{no}")
	public String order(@PathVariable("no") int productNo, Model model) {
		model.addAttribute("productNo", productNo);
		return "view/order/main";
	}
	@GetMapping("/admin/addForm")
	public String productAddForm() {
		return "/view/product/addForm";
	}
	@GetMapping("/credit")
	public String credit() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		System.out.println(user);
		
		return "view/order/credit";
	}
	
	
}
