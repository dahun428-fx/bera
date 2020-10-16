package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
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
		
		if(order == null || user == null) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "실패하였습니다.");
			return resultMap;
		}
		
		Cart cart = new Cart();
		cart.setAmount(order.getOrderProductAmount());
		cart.setProductNo(order.getProductNo());
		cart.setUserId(user.getId());
		cartDao.insert(cart);
		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "장바구니에 등록되었습니다.");
		
		return resultMap;
	}
	public Map<String, Object> list(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("list", cartDao.getAllCarts(param));
		return resultMap;
	}

	
	
}
