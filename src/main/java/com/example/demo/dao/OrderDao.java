package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Order;
import com.example.demo.vo.OrderDetail;

@Mapper
public interface OrderDao {

	void insertOrderSub(Order order);
	void insertOrderMain(OrderDetail orderDetail);
	List<Order> getOrderByNo(int orderNo);
	OrderDetail getOrderDetail(Map<String, Object> param);
}
