package com.example.demo.vo;

import java.util.Date;

public class OrderDetail {
	private int orderNo;
	private String userId;
	private int orderUsingPoint;
	private int orderPayment;
	private Date regDate;
	private String completed;
	
	public OrderDetail() {}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getOrderUsingPoint() {
		return orderUsingPoint;
	}

	public void setOrderUsingPoint(int orderUsingPoint) {
		this.orderUsingPoint = orderUsingPoint;
	}

	public int getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(int orderPayment) {
		this.orderPayment = orderPayment;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "OrderDetail [orderNo=" + orderNo + ", userId=" + userId + ", orderUsingPoint=" + orderUsingPoint
				+ ", orderPayment=" + orderPayment + ", regDate=" + regDate + ", completed=" + completed + "]";
	}
	
}
