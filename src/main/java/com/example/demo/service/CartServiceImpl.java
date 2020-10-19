package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.ProductDTO;
import com.example.demo.form.SearchForm;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Order;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.ProductImage;
import com.example.demo.vo.User;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	public Map<String, Object> add(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		Order order = (Order) map.get("order");
		User user = (User) map.get("user");
		
		if(order == null || user == null) {
			resultMap.put("isSuccess", "fail");
			resultMap.put("msg", "실패하였습니다.");
			return resultMap;
		}
		
		Cart savedCart = cartDao.getCart(order.getProductNo(), user.getId());
		if(savedCart != null) {
			savedCart.setAmount(savedCart.getAmount() + order.getOrderProductAmount());
			cartDao.updateAmount(savedCart);
			resultMap.put("isSuccess", "success");
			resultMap.put("msg", "수량이 추가되었습니다.");
			return resultMap;
		}
		Cart cart = new Cart();
		cart.setAmount(order.getOrderProductAmount());
		cart.setProductNo(order.getProductNo());
		cart.setUserId(user.getId());
		cartDao.insert(cart);
		resultMap.put("isSuccess", "success");
		resultMap.put("msg", "장바구니에 등록되었습니다.");
		
		return resultMap;
	}
	public Map<String, Object> list(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		Map<String, Object> param = new HashMap<>();
		param.put("query", "getCartByUserId");
		param.put("userId", map.get("userId"));
		param.put("searchForm", map.get("searchForm"));
		
		Pagination pagination = this.getPagination(param);
		param.put("pagination", pagination);
		
		List<Cart> cartList = cartDao.getAllCarts(param);
		List<ProductDTO> productList = new ArrayList<>();
		List<ProductImage> imageList = new ArrayList<>();
		for(Cart cart : cartList) {
			ProductDTO product = productDao.getProductByNo(cart.getProductNo());
			productList.add(product);
		}
		for(ProductDTO dto : productList) {
			imageList.add(dto.getProductImage());
		}
		
		resultMap.put("cartList", cartList);
		resultMap.put("productList", productList);
		resultMap.put("imageList", imageList);
		resultMap.put("pagination", pagination);
		return resultMap;
	}

	private int totalListCount(Map<String, Object> param) {
		List<Cart> carts = cartDao.getAllCarts(param);
		int totalCount = carts.size();
		return totalCount;
	}
	private Pagination getPagination(Map<String, Object> param) {
		int totalRows = this.totalListCount(param);
		SearchForm searchForm = (SearchForm) param.get("searchForm");
		int rowsPerPage = (searchForm.getRowsPerPage() != 0) ? searchForm.getRowsPerPage() : 5;
		int pagesPerBlock = (searchForm.getPagesPerBlock() != 0) ? searchForm.getPagesPerBlock(): 5;
		int pageNo = (searchForm.getPageNo() != 0) ? searchForm.getPageNo() : 1;
		int endIndex = (searchForm.getEndIndex() != 0) ? searchForm.getEndIndex() : 5;
		Pagination pagination = new Pagination(rowsPerPage, pagesPerBlock, pageNo, totalRows);
		pagination.setEndIndex(endIndex);
		
		return pagination;
	}
	
}
