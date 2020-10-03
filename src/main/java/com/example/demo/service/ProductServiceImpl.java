package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductDTO;
import com.example.demo.form.ProductForm;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Product;
import com.example.demo.vo.ProductImage;
import com.example.demo.vo.ProductTag;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private FileStorageService fileService;
	
//	private static final String location = "C:\\APP\\spring-workspace\\bera\\src\\main\\resources\\static\\upload\\img";
	@Override
	public List<ProductDTO> list() {
		Map<String, Object> map = new HashMap<>();
		
		//Pagination pagination = new Pagination(rowsPerPage, pagesPerBlock, pageNo, totalRows);
		
		return productDao.getAllProducts(map);
	}
	
	
	@Transactional
	public Map<String, Object> add(ProductForm productForm) {

		Map<String, Object> resultMap = new HashMap<>();
		if(productForm == null) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "입력이 잘못되었습니다.");
			return resultMap;
		}
		
		Product product = new Product();
		product.setName(productForm.getName());
		product.setAmount(product.getAmount());
		product.setPrice(productForm.getPrice());
		product.setPoint(productForm.getPoint());
		product.setDiscountPrice(productForm.getDiscountPrice());
		product.setCategory(productForm.getCategory());
		product.setExplain(productForm.getExplain());
		productDao.insert(product);
		ProductImage productImage = new ProductImage();
			
		MultipartFile upFile = productForm.getUpload();
		if(upFile.isEmpty()) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "등록에 실패하였습니다.");
			return resultMap;
		}
		String filename = fileService.storeFile(upFile);
		productForm.setImagePath(filename);
		productImage.setNo(product.getNo());
		productImage.setImagePath(productForm.getImagePath());
		productDao.insertImage(productImage);
		
		String[] tags = productForm.getTagArray();
		ProductTag productTag = new ProductTag();
		productTag.setNo(product.getNo());
		productTag.setTags(tags);
		System.out.println("tags : " + productTag);
		productDao.insertTag(productTag);
		
		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "등록되었습니다.");
		return resultMap;
		
	}
}
