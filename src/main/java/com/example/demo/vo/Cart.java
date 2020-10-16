package com.example.demo.vo;

import java.util.Date;

public class Cart {

	private int no;
	private String userId;
	private int productNo;
	private int amount;
	private Date regDate;
	
	public Cart() {
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Cart [no=" + no + ", userId=" + userId + ", productNo=" + productNo + ", amount=" + amount + "]";
	}
	
	
}
