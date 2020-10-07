package com.example.demo.form;

public class SearchForm {

	private int pageNo;
	private String searchType;
	private String searchValue;
	private String formType;
	private String listType;
	private String orderby;
	private int rowsPerPage;
	private int pagesPerBlock;
	private int endIndex;
	
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

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getPagesPerBlock() {
		return pagesPerBlock;
	}

	public void setPagesPerBlock(int pagesPerBlock) {
		this.pagesPerBlock = pagesPerBlock;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	@Override
	public String toString() {
		return "SearchForm [pageNo=" + pageNo + ", searchType=" + searchType + ", searchValue=" + searchValue
				+ ", formType=" + formType + ", listType=" + listType + ", orderby=" + orderby + ", rowsPerPage="
				+ rowsPerPage + ", pagesPerBlock=" + pagesPerBlock + ", endIndex=" + endIndex + "]";
	}

	
	
	
}
