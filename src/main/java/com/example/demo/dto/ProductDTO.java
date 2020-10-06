package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.example.demo.vo.ProductImage;
import com.example.demo.vo.ProductTag;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductDTO {

	private int no;
	private String name;
	private int amount;
	private int price;
	private double point;
	private double discountPrice;
	private String category;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date regDate;
	private int reviews;
	private String explain;
	private String isAvailable;
	private ProductImage productImage;
	private List<ProductTag> productTag;

	public ProductDTO() {}


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
	public ProductImage getProductImage() {
		return productImage;
	}
	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public List<ProductTag> getProductTag() {
		return productTag;
	}

	public void setProductTag(List<ProductTag> productTag) {
		this.productTag = productTag;
	}


	@Override
	public String toString() {
		return "ProductDTO [no=" + no + ", name=" + name + ", amount=" + amount + ", price=" + price + ", point="
				+ point + ", discountPrice=" + discountPrice + ", category=" + category + ", regDate=" + regDate
				+ ", reviews=" + reviews + ", explain=" + explain + ", isAvailable=" + isAvailable + ", productImage="
				+ productImage + ", productTag=" + productTag + "]";
	}

	
}
