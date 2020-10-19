package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Point;

@Mapper
public interface PointDao {

	void insert(Point point);
	
}
