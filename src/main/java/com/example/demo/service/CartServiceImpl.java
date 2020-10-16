package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.UserDao;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Order;
import com.example.demo.vo.User;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartDao cartDao;
	
	public Map<String, Object> add(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		Order order = (Order) map.get("order");
		User user = (User) map.get("user");
		
		Cart cart = new Cart();
		cart.setAmount(order.getOrderProductAmount());
		cart.setProductNo(order.getProductNo());
		cart.setUserId(user.getId());
		cartDao.insert(cart);
		
		return resultMap;
	}
}
