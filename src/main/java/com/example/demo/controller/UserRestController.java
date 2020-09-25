package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.vo.User;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@GetMapping("/login")
	public User login() {
		User user = null;
		return user;
	}
	
}
