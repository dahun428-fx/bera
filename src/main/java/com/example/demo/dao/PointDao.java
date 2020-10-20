package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Point;

@Mapper
public interface PointDao {

	void insert(Point point);
	List<Point> get(String userId);
}
