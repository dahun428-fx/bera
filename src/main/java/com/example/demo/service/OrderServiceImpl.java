package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.form.OrderForm;
import com.example.demo.vo.Order;

@Service
public class OrderServiceImpl implements OrderService {

	public Map<String, Object> add(OrderForm orderForm) {
		Map<String, Object> resultMap = new HashMap<>();
		List<Order> orders = orderForm.getOrders();
		
		
		return resultMap;
	}
}
