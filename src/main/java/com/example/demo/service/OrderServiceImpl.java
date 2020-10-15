package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.ProductDTO;
import com.example.demo.form.OrderForm;
import com.example.demo.vo.Order;
import com.example.demo.vo.OrderDetail;
import com.example.demo.vo.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;
	
	@Transactional
	public Map<String, Object> add(OrderForm orderForm) {
		Map<String, Object> resultMap = new HashMap<>();
		
		if(orderForm.getOrders().isEmpty()) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "주문 목록이 없습니다. 다시 주문해주세요");
			return resultMap;
		}
		if(orderForm.getOrderUsingPoint() < 0) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "잘못된 접근입니다. 다시 입력해주세요");
			return resultMap;
		}
		
		//orderList get
		List<Order> orders = orderForm.getOrders();
		
		//orderMain table insert
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setUserId(orderForm.getUserId());
		orderDetail.setOrderUsingPoint(orderForm.getOrderUsingPoint());
		orderDetail.setOrderPayment(orderForm.getOrderPayment());
		orderDao.insertOrderMain(orderDetail);
		
		//결제된 order product Point user 에 save
		int savedPoint = 0;
		for(Order order : orders) {
			order.setNo(orderDetail.getOrderNo());
			ProductDTO product = productDao.getProductByNo(order.getProductNo());
			savedPoint += product.getPoint();
			orderDao.insertOrderSub(order);
		}

		//user객체를 찾아서 update
		Map<String, Object> param = new HashMap<>();
		param.put("query", "getUserById");
		param.put("userId", orderForm.getUserId());
		
		User user = userDao.getUser(param);
		user.setPoint(savedPoint);
		userDao.updateUser(user);

		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "주문 완료 되었습니다.");
		return resultMap;
	}
}
