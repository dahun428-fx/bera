package com.example.demo.form;

import java.util.Date;
import java.util.List;

import com.example.demo.vo.Order;

public class OrderForm {
	private List<Order> orders;
	private String userId;
	private int orderUsingPoint;
	private int orderPayment;
	private Date regDate;
	private String completed;
	private String orderType;
	
	public OrderForm() {
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "OrderForm [orders=" + orders + ", userId=" + userId + ", orderUsingPoint=" + orderUsingPoint
				+ ", orderPayment=" + orderPayment + ", regDate=" + regDate + ", completed=" + completed
				+ ", orderType=" + orderType + "]";
	}

}
