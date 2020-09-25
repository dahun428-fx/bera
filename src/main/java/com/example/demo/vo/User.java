package com.example.demo.vo;

import java.util.Date;

public class User {
	/*
	 * user_id varchar2(30) primary key, name varchar2(10) not null, password
	 * varchar2(50) not null, email varchar2(50) not null, deleted char(1) default
	 * 'N', admin char(1) default 'N', reg_date date default sysdate
	 */
	private String id;
	private String name;
	private String password;
	private String phone;
	private String email;
	private int point;
	private String deleted;
	private String admin;
	private Date regDate;
	
	public User() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}


