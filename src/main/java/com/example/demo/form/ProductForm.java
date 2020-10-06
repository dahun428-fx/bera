package com.example.demo.form;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {

	private int no;
	private String name;
	private String category;
	private int price;
	private int discountPrice;
	private double point;
	private int amount;
	private String explain;
	private String[] tagArray;
	private String imagePath;
	private MultipartFile upload;
	
	public ProductForm() {}

	
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String[] getTagArray() {
		return tagArray;
	}

	public void setTagArray(String[] tagArray) {
		this.tagArray = tagArray;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public MultipartFile getUpload() {
		return upload;
	}

	public void setUpload(MultipartFile upload) {
		this.upload = upload;
	}


	@Override
	public String toString() {
		return "ProductForm [no=" + no + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", discountPrice=" + discountPrice + ", point=" + point + ", amount=" + amount + ", explain="
				+ explain + ", tagArray=" + Arrays.toString(tagArray) + ", imagePath=" + imagePath + ", upload="
				+ upload + "]";
	}

}
