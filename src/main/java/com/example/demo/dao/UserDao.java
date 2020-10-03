package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.User;
import com.example.demo.vo.UserRole;

@Mapper
public interface UserDao {

	void insertUser(User user);
	User getUser(Map<String, Object> map);
	List<User> getUsers();
	void deleteUser(User user);
	void updateUser(User user);
	
	List<UserRole> getUserRolesByUserId(String userId);
	void insertUserRole(@Param("userId") String userId,
							@Param("roleName") String roleName);
	
}
