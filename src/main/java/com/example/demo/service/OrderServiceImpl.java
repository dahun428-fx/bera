package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.PointDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.ProductDTO;
import com.example.demo.form.OrderForm;
import com.example.demo.vo.Order;
import com.example.demo.vo.OrderDetail;
import com.example.demo.vo.Point;
import com.example.demo.vo.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private PointDao pointDao;
	
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
		Map<String, Object> param = new HashMap<>();
		param.put("query", "getUserById");
		param.put("userId", orderForm.getUserId());
		User savedUser = userDao.getUser(param);
		if(savedUser == null) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "해당 유저 정보가 없습니다.");
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
		
		//user point 사용시 제거
		System.out.println("point :" + orderForm.getOrderUsingPoint());
		System.out.println("user : " + savedUser);
		if(orderForm.getOrderUsingPoint() > 0) {
			savedUser.setPoint(savedUser.getPoint() - orderForm.getOrderUsingPoint());
			userDao.updateUser(savedUser);

			Point point = new Point();
			point.setUserId(savedUser.getId());
			point.setUsedPoint(orderForm.getOrderUsingPoint());
			point.setUsedReason("상품 구매");
			pointDao.insert(point);
		}
		//cart에서 주문시 cart 목록 제거
		if("cart".equals(orderForm.getOrderType())) {
			for(Order order : orders) {
				cartDao.delete(order.getProductNo(), savedUser.getId());
			}
		}
		
		//결제된 order product Point user 에 save
		int savedPoint = 0;
		for(Order order : orders) {
			order.setNo(orderDetail.getOrderNo());
			ProductDTO product = productDao.getProductByNo(order.getProductNo());
			savedPoint += product.getPoint();
			orderDao.insertOrderSub(order);
		}
		System.out.println("savedPoint : " + savedPoint);

		//user객체를 찾아서 update
		savedUser.setPoint(savedUser.getPoint() + savedPoint);
		userDao.updateUser(savedUser);

		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "주문 완료 되었습니다.");
		return resultMap;
	}
}
