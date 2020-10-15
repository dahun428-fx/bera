package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Order;
import com.example.demo.vo.OrderDetail;

@Mapper
public interface OrderDao {

	void insertOrderSub(Order order);
	void insertOrderMain(OrderDetail orderDetail);
}
