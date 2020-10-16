package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Cart;

@Mapper
public interface CartDao {

	void insert(Cart cart);
	List<Cart> getAllCarts(Map<String, Object> param);
	
}
