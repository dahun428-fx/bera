package com.example.demo.vo;

import java.util.Arrays;

import org.apache.ibatis.annotations.Mapper;

public class ProductTag {

	private int no;
	private String[] tags;
	private String tag;
	
	public ProductTag() {}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "ProductTag [no=" + no + ", tags=" + Arrays.toString(tags) + ", tag=" + tag + "]";
	}

	
	
	
}
