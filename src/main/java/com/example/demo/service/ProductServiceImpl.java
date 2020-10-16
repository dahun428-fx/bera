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
import com.example.demo.form.SearchForm;
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

	
	public ProductDTO get(int productNo) {
		return productDao.getProductByNo(productNo);
	}
	public int totalListCount(Map<String, Object> param) {
		List<ProductDTO> products = productDao.getAllProducts(param);
		int totalCount = products.size();
		return totalCount;
	}
	@Transactional
	public Map<String, Object> delete(int no) {
		Map<String, Object> resultMap = new HashMap<>();
		
		ProductDTO savedProduct = productDao.getProductByNo(no);
		if(savedProduct == null) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "해당 상품이 없습니다. 삭제에 실패하였습니다.");
			return resultMap;
		}
		ProductImage savedImage = productDao.getProductImageByNo(no);
		if(savedImage != null) {
			productDao.deleteImagePathByNo(no);
		}
		List<ProductTag> savedTags =  productDao.getProductTagsByNo(no);
		if(!savedTags.isEmpty()) {
			productDao.deleteTagsByNo(no);
		}
		productDao.delete(no);
		
		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "삭제되었습니다.");
		return resultMap;
	}
	
	public Map<String, Object> list(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		if("getProductImages".equals(map.get("query"))) {
			List<ProductImage> list = productDao.getProductImages();
			resultMap.put("imageList", list);
			return resultMap;
		};
		
		Map<String, Object> param = new HashMap<>();
		SearchForm searchForm = (SearchForm) map.get("searchForm");
		if(searchForm != null) {
			param.put("searchType", searchForm.getSearchType());
			param.put("searchValue", searchForm.getSearchValue());
			param.put("formType", searchForm.getFormType());
			param.put("listType", searchForm.getListType());
		}
		int totalRows = this.totalListCount(param);
		int rowsPerPage = (searchForm.getRowsPerPage() != 0) ? searchForm.getRowsPerPage() : 5;
		int pagesPerBlock = (searchForm.getPagesPerBlock() != 0) ? searchForm.getPagesPerBlock(): 5;
		int pageNo = (searchForm.getPageNo() != 0) ? searchForm.getPageNo() : 1;
		
		Pagination pagination = new Pagination(rowsPerPage, pagesPerBlock, pageNo, totalRows);
		int endIndex = (searchForm.getEndIndex() != 0) ? searchForm.getEndIndex() : 5;
		pagination.setEndIndex(endIndex);
		param.put("pagination", pagination);
		List<ProductDTO> list = productDao.getAllProducts(param);
		
		resultMap.put("list", list);
		resultMap.put("pagination", pagination);
		
		return resultMap;
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
		product.setAmount(productForm.getAmount());
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
		
		
		fileService.setDirectory(productForm.getCategory());
		String filename = fileService.storeFile(upFile);
		productForm.setImagePath(filename);
		productImage.setNo(product.getNo());
		productImage.setImagePath(productForm.getImagePath());
		productDao.insertImage(productImage);
		
		if(productForm.getTagArray().length > 0) {
			
			String[] tags = productForm.getTagArray();
			ProductTag productTag = new ProductTag();
			productTag.setNo(product.getNo());
			productTag.setTags(tags);
			productDao.insertTag(productTag);
		}
		
		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "등록되었습니다.");
		return resultMap;
		
	}
	@Transactional
	public Map<String, Object> update(ProductForm productForm) {
		Map<String, Object> resultMap = new HashMap<>();
		
		if(productForm.getIsAvailable() != null) {
			
			Product product = new Product();
			product.setNo(productForm.getNo());
			product.setIsAvailable(productForm.getIsAvailable());
			System.out.println("product : " + product);
			productDao.updateAvailable(product);
			
			resultMap.put("isSuccess", "success");
			resultMap.put("msg", "성공하였습니다.");
			return resultMap;
		}
		
		Product product = new Product();
		product.setNo(productForm.getNo());
		product.setName(productForm.getName());
		product.setAmount(productForm.getAmount());
		product.setPoint(productForm.getPoint());
		product.setPrice(productForm.getPrice());
		product.setDiscountPrice(productForm.getDiscountPrice());
		product.setCategory(productForm.getCategory());
		product.setExplain(productForm.getExplain());
		productDao.update(product);
		
		String savedImagePath = productDao.getProductImageByNo(product.getNo()).getImagePath();
		
		if(!savedImagePath.equals(productForm.getImagePath())) {
			productDao.deleteImagePathByNo(product.getNo());
			
			ProductImage productImage = new ProductImage();
			MultipartFile upFile = productForm.getUpload();
			if(upFile == null) {
				resultMap.put("isSuccess", "fail");
				resultMap.put("msg", "수정에 실패했습니다.");
				return resultMap;
			}
			fileService.setDirectory(product.getCategory());
			String imagePath = fileService.storeFile(upFile);
			productForm.setImagePath(imagePath);
			productImage.setNo(productForm.getNo());
			productImage.setImagePath(productForm.getImagePath());
			productDao.insertImage(productImage);
		}
		productDao.deleteTagsByNo(productForm.getNo());
		
		if(productForm.getTagArray().length > 0) {
			String[] tags = productForm.getTagArray();
			ProductTag productTag = new ProductTag();
			productTag.setNo(product.getNo());
			productTag.setTags(tags);
			productDao.insertTag(productTag);
		}
		
		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "수정 완료되었습니다.");
		return resultMap;
	}
	
}
