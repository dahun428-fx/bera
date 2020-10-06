package com.example.demo.form;

public class SearchForm {

	private int pageNo;
	private String searchType;
	private String searchValue;
	
	
	public SearchForm() {
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	

	@Override
	public String toString() {
		return "SearchForm [pageNo=" + pageNo + ", searchType=" + searchType + ", searchValue=" + searchValue + "]";
	}
	
	
}
