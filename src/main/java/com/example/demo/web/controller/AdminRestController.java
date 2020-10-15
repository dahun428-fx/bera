package com.example.demo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@GetMapping("/admin/addForm")
	public ModelAndView productAddForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/view/product/addForm");
		return mav;
	}
	
}
