package com.example.demo.service;

import java.util.Map;

import com.example.demo.vo.Cart;
import com.example.demo.vo.Order;

public interface CartService {

	Map<String, Object> add(Map<String, Object> param);
	Map<String, Object> list(Map<String, Object> param);
}
