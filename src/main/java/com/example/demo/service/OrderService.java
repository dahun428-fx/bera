package com.example.demo.service;

import java.util.Map;

import com.example.demo.form.OrderForm;

public interface OrderService {
	Map<String, Object> add(OrderForm orderForm);
}
