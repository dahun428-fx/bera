package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.vo.Product;
import com.example.demo.vo.ProductImage;
import com.example.demo.vo.ProductTag;

@Mapper
public interface ProductDao {

	void insert(Product product);
	void insertImage(ProductImage productImage);
	void insertTag(ProductTag productTag);
	List<ProductDTO> getAllProducts(Map<String, Object> map);
	ProductImage getProductImageByNo(int no);
	List<ProductTag> getProductTagsByNo(int no);
	ProductDTO getProductByNo(int no);
	
	void update(Product product);
	void deleteImagePathByNo(int no);
	void deleteTagsByNo(int no);
	void delete(int no);
}
