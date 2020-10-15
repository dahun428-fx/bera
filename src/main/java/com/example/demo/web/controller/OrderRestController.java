package com.example.demo.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.form.OrderForm;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.vo.Order;
import com.example.demo.vo.Product;
import com.example.demo.vo.User;

@RestController
@RequestMapping("/order")
@SessionAttributes({"ORDER_LIST"})
public class OrderRestController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/buy/{no}")
	public ModelAndView buyView(Model model, @PathVariable("no") int productNo) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("productNo", productNo);
		mav.setViewName("view/order/main");
		return mav;
	}
	
	@PostMapping("/buy")
	public Map<String, Object> buy(@RequestBody OrderForm orderForm, Model model) {
		Map<String, Object> resultMap = new HashMap<>();
		
		List<Order> orders = orderForm.getOrders();
		model.addAttribute("ORDER_LIST", orders);
		if(!orders.isEmpty()) {
			resultMap.put("isSuccess", "success");
		} else {
			resultMap.put("isSuccess", "fail");
		}
		
		return resultMap;
	}
	@GetMapping("/credit")
	public ModelAndView creditView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view/order/credit");
		return mav;
	}
	@GetMapping("/products")
	public Map<String, Object> credit(HttpSession session){
		Map<String, Object> resultMap = new HashMap<>();
		List<Order> orders = (List<Order>) session.getAttribute("ORDER_LIST");
		
		List<OrderDTO> orderList = new ArrayList<>();
		
		for(Order order : orders) {
			ProductDTO productDTO = productService.get(order.getProductNo());
			OrderDTO orderDto = new OrderDTO();
			orderDto.setProductNo(order.getProductNo());
			orderDto.setOrderAmount(order.getOrderProductAmount());
			orderDto.setProductName(productDTO.getName());
			orderDto.setProductPoint(productDTO.getPoint());
			orderDto.setProductDiscountPrice(productDTO.getDiscountPrice());
			orderDto.setProductImagePath(productDTO.getProductImage().getImagePath());
			orderDto.setProductPrice(productDTO.getPrice());
			orderDto.setProductCategory(productDTO.getCategory());
			orderList.add(orderDto);
		}
	
		resultMap.put("userInfo", userService.getLoginedUser());
		resultMap.put("orderList", orderList);
		
		return resultMap;
	}
	@PostMapping("/addOrder")
	@ResponseBody
	public Map<String, Object> addOrder(@RequestBody OrderForm orderForm){
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println(orderForm);
		
		return orderService.add(orderForm);
	}
	@GetMapping("/complete")
	public ModelAndView completeView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view/order/complete");
		return mav;
	}
}
