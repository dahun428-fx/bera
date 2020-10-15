package com.example.demo.dto;

public class OrderDTO {

	private int productNo;
	private String productName;
	private int orderAmount;
	private int productPrice;
	private double productDiscountPrice;
	private double productPoint;
	private String productImagePath;
	private String productCategory;
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public double getProductDiscountPrice() {
		return productDiscountPrice;
	}
	public void setProductDiscountPrice(double productDiscountPrice) {
		this.productDiscountPrice = productDiscountPrice;
	}
	public double getProductPoint() {
		return productPoint;
	}
	public void setProductPoint(double productPoint) {
		this.productPoint = productPoint;
	}
	public String getProductImagePath() {
		return productImagePath;
	}
	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	@Override
	public String toString() {
		return "OrderDTO [productNo=" + productNo + ", productName=" + productName + ", orderAmount=" + orderAmount
				+ ", productPrice=" + productPrice + ", productDiscountPrice=" + productDiscountPrice
				+ ", productPoint=" + productPoint + ", productImagePath=" + productImagePath + "]";
	}

	
}
