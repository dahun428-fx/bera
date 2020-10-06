package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.ProductDTO;
import com.example.demo.form.ProductForm;
import com.example.demo.vo.Product;

public interface ProductService {

	Map<String, Object> add(ProductForm productForm);
	Map<String, Object> list(Map<String, Object> param);
	int totalListCount(Map<String, Object> param);
	ProductDTO get(int productNo);
	Map<String, Object> update(ProductForm productForm);
	Map<String, Object> delete(int no);
}
