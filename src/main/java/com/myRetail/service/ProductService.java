package com.myRetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myRetail.entity.ProductEntity;
import com.myRetail.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public ProductEntity findByProductId(Integer productId) {
		return productRepository.findByProductId(productId);
	}
	
	public boolean productExists(Integer productId) {
		return productRepository.exists(productId);
	}
	
	public void saveProduct(ProductEntity product) {
		productRepository.save(product);
	}

}
