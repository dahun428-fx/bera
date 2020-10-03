package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.ProductDTO;
import com.example.demo.form.ProductForm;
import com.example.demo.vo.Product;

public interface ProductService {

	Map<String, Object> add(ProductForm productForm);
	List<ProductDTO> list();
}
