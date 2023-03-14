package com.model2.mvc.service.product.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.ProductDao;;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao prodDao;
	public void setProductDao(ProductDao prodDao) {
		this.prodDao = prodDao;
	}
	
	///Constructor
		public ProductServiceImpl() {
			System.out.println(this.getClass());
		}
		
	@Override
	public Product addProduct(Product prod) throws Exception {
		// TODO Auto-generated method stub
		prodDao.addProduct(prod);
		return prod;
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return prodDao.getProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		List<Product> list= prodDao.getProductList(search);
		int totalCount = prodDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list",list);
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map¹ÝÈ¯2");
		return map;
	}

	@Override
	public Product updateProduct(Product prod) throws Exception {
		prodDao.updateProduct(prod);
		return prod;
	}
}
