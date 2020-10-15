package com.example.demo.vo;

public class Order {

	private int no;
	private int productNo;
	private int productPrice;
	private int orderProductAmount;
	
	public Order() {
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getOrderProductAmount() {
		return orderProductAmount;
	}

	public void setOrderProductAmount(int orderProductAmount) {
		this.orderProductAmount = orderProductAmount;
	}

	@Override
	public String toString() {
		return "Order [no=" + no + ", productNo=" + productNo + ", productPrice=" + productPrice
				+ ", orderProductAmount=" + orderProductAmount + "]";
	}
	
	
}
