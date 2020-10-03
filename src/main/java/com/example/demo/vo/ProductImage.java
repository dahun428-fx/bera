package com.example.demo.vo;

public class ProductImage {

	private int no;
	private String imagePath;
	
	public ProductImage() {}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "ProductImage [no=" + no + ", imagePath=" + imagePath + "]";
	}
	
}
