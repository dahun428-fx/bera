package com.example.demo.form;

public class SearchForm {

	private int pageNo;
	private String searchType;
	private String searchValue;
	private String formType;
	private String listType;
	
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
	
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	@Override
	public String toString() {
		return "SearchForm [pageNo=" + pageNo + ", searchType=" + searchType + ", searchValue=" + searchValue + "]";
	}
	
	
}
