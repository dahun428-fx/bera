package com.example.demo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProductDTO;
import com.example.demo.form.ProductForm;
import com.example.demo.form.SearchForm;
import com.example.demo.service.ProductService;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Product;

@RestController
@RequestMapping("/product")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/main") 
	public ModelAndView mainView(Model model){
		ModelAndView mav = new ModelAndView();
		model.addAttribute("uriType", "main");
		mav.setViewName("view/product/list");
		return mav;
	}
	@GetMapping("/icecream")
	public ModelAndView icecreamView(Model model) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("uriType", "icecream");
		mav.setViewName("view/product/list");
		return mav;
	}
	@GetMapping("/cake")
	public ModelAndView cakeView(Model model) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("uriType", "cake");
		mav.setViewName("/view/product/list");
		return mav;
	}
	@GetMapping("/menu/{no}")
	public ModelAndView detailView(Model model, @PathVariable("no") int productNo) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("productNo", productNo);
		mav.setViewName("view/product/detail");
		return mav;
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> insertProduct(ProductForm productForm) {
		return productService.add(productForm);
	}
	@PostMapping("/products")
	@ResponseBody
	public Map<String, Object> getProducts(SearchForm searchForm){
		Map<String, Object> resultMap = new HashMap<>();
		
		Map<String, Object> param = new HashMap<>();
		param.put("searchForm", searchForm);

		Map<String, Object> map = productService.list(param);
		resultMap.put("list", map.get("list"));
		resultMap.put("pagination", map.get("pagination"));
		
		return resultMap;
	}
	@GetMapping("/images")
	@ResponseBody
	public Map<String, Object> getProductImages(){
		Map<String, Object> resultMap = new HashMap<>();
		
		Map<String, Object> param = new HashMap<>();
		param.put("query", "getProductImages");
		Map<String, Object> map = productService.list(param);
		resultMap.put("imageList", map.get("imageList"));
		
		return resultMap;
	}
	@GetMapping("/products/{no}")
	@ResponseBody
	public ProductDTO getProduct(@PathVariable("no") int productNo) {
		return productService.get(productNo);
	}
	@PutMapping("/update")
	@ResponseBody
	public Map<String, Object> updateProducts(ProductForm productForm){
		System.out.println("controller product form : " + productForm);
		return productService.update(productForm);
	}
	@DeleteMapping("/products/{no}")
	@ResponseBody
	public Map<String, Object> deleteProduct(@PathVariable("no") int productNo){
		return productService.delete(productNo);
	}
}
