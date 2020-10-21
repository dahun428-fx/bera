package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.example.demo.vo.Product;

public class PointDTO {

	//주문번호
	private int orderNo;
	//상품
	private List<ProductDTO> product;
	//수량
	private int orderAmount;
	//구매가격
	private int orderTotalPrice;
	//사용한포인트
	private double usedPoint;
	//사용일자
	private Date usedDate;
	private String userId;
	private String usedReason;
	
	
	public PointDTO() {}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}


	public List<ProductDTO> getProduct() {
		return product;
	}

	public void setProduct(List<ProductDTO> product) {
		this.product = product;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(int orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public double getUsedPoint() {
		return usedPoint;
	}

	public void setUsedPoint(double usedPoint) {
		this.usedPoint = usedPoint;
	}

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsedReason() {
		return usedReason;
	}

	public void setUsedReason(String usedReason) {
		this.usedReason = usedReason;
	}

	@Override
	public String toString() {
		return "PointDTO [orderNo=" + orderNo + ", product=" + product + ", orderAmount=" + orderAmount
				+ ", orderTotalPrice=" + orderTotalPrice + ", usedPoint=" + usedPoint + ", usedDate=" + usedDate
				+ ", userId=" + userId + ", usedReason=" + usedReason + "]";
	}

	

	
}
