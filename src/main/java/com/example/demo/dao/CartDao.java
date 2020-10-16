package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Cart;

@Mapper
public interface CartDao {

	void insert(Cart cart);
	
}
