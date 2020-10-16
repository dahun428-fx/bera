package com.example.demo.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProductDTO;
import com.example.demo.service.CartService;
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
		param.put("query", "getCartByUserId");
		param.put("userId", savedUser.getId());
		List<Cart> cartList = (List<Cart>) cartService.list(param).get("list");
		List<ProductDTO> productList = new ArrayList<>();
		for(Cart cart : cartList) {
			ProductDTO product = productService.get(cart.getProductNo());
			productList.add(product);
		}
		List<ProductImage> imageList = new ArrayList<>();
		for(ProductDTO dto : productList) {
			imageList.add(dto.getProductImage());
		}
		resultMap.put("imageList", imageList);
		resultMap.put("productList", productList);
		resultMap.put("cartList", cartList);
		resultMap.put("userInfo", savedUser);
		return resultMap;
	}
}
