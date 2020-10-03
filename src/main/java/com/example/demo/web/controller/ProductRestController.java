package com.example.demo.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.ProductDTO;
import com.example.demo.form.ProductForm;
import com.example.demo.service.ProductService;
import com.example.demo.vo.Product;

@Controller
@RequestMapping("/product")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> insertProduct(ProductForm productForm, HttpServletRequest request) {
		return productService.add(productForm);
	}
	@GetMapping("/products")
	@ResponseBody
	public List<ProductDTO> getProducts(){
		return productService.list();
	}
	
}
