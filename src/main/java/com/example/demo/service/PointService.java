package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.vo.Point;

public interface PointService {

	Map<String, Object> getPoints(String userId);
	
}
