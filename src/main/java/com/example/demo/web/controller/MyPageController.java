package com.example.demo.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProductDTO;
import com.example.demo.form.SearchForm;
import com.example.demo.service.CartService;
import com.example.demo.service.PointService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.vo.Cart;
import com.example.demo.vo.ProductImage;
import com.example.demo.vo.User;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PointService pointService;
	
	
	@GetMapping("/point")
	public ModelAndView pointView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view/mypage/point");
		return mav;
	}
	
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
		
		Map<String, Object> param = new HashMap<>();
		param.put("userId", savedUser.getId());
	
		Map<String, Object> map = cartService.list(param);
		resultMap.put("cartList", map.get("cartList"));
		resultMap.put("productList", map.get("productList"));
		resultMap.put("imageList", map.get("imageList"));
		resultMap.put("userInfo", savedUser);
		//resultMap.put("pagination", map.get("pagination"));
		return resultMap;
	}
	@GetMapping("/points")
	@ResponseBody
	public Map<String, Object> getPoints(){
		User savedUser = userService.getLoginedUser();
		return pointService.getPoints(savedUser.getId());
	}
	
}
