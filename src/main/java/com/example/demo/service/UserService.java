package com.example.demo.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.vo.User;

public interface UserService extends UserDetailsService {

	Map<String, Object> join(User user);
}
