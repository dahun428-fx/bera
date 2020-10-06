package com.example.demo.vo;

import java.util.Date;

/*
 * 
 * 	product_no INT auto_increment PRIMARY key,
	`name` VARCHAR(100),
	amount INT,
	price INT,
	`point` INT,
	discount_price INT,
	category VARCHAR(100),
	reg_date date default NOW(),
	reviews INT DEFAULT 0,
	`explain` VARCHAR(1000)
 */
public class Product {

	private int no;
	private String name;
	private int amount;
	private int price;
	private double point;
	private double discountPrice;
	private String category;
	private Date regDate;
	private int reviews;
	private String explain;
	private String isAvailable;
	
	public Product() {}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getReviews() {
		return reviews;
	}

	public void setReviews(int reviews) {
		this.reviews = reviews;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Product [no=" + no + ", name=" + name + ", amount=" + amount + ", price=" + price + ", point=" + point
				+ ", discountPrice=" + discountPrice + ", category=" + category + ", regDate=" + regDate + ", reviews="
				+ reviews + ", explain=" + explain + ", isAvailable=" + isAvailable + "]";
	}
	
}
