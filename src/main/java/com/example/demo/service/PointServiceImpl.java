package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.PointDao;
import com.example.demo.dao.UserDao;
import com.example.demo.vo.OrderDetail;
import com.example.demo.vo.Point;
import com.example.demo.vo.User;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	private PointDao pointDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrderDao orderDao;
	
	public Map<String, Object> getPoints(String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		
		
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("query", "getUserById");
		//유저 있는지
		User savedUser = userDao.getUser(param);
		if(savedUser == null) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "해당 유저가 존재하지 않습니다.");
			return resultMap;
		}
		//포인트 내역
		List<Point> points = pointDao.get(userId);
		
		//주문 내역
		param.put("query", "getOrderByUserId");
		OrderDetail order = orderDao.getOrderDetail(param);
		
		//주문 수량
		int orderCount = orderDao.getOrderByNo(order.getOrderNo()).size();
		
		resultMap.put("isSuccess", "success");
		resultMap.put("pointList", points);
		resultMap.put("pointOrder", order);
		resultMap.put("orderCount", orderCount);
		
		return resultMap;
	}
	
}
