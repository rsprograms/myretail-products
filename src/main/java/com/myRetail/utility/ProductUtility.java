package com.myRetail.utility;

import org.springframework.stereotype.Component;

import com.myRetail.entity.ProductEntity;
import com.myRetail.model.ProductPrice;
import com.myRetail.model.Product;

@Component
public class ProductUtility {
	
	public static Product convertEntityToProduct (ProductEntity productEntity, String productName) {
		Product product = new Product();
		product.setId(productEntity.getProductId());
		product.setName(productName);
		
		ProductPrice productPrice = new ProductPrice();
		productPrice.setValue(productEntity.getAmount());
		productPrice.setCurrency_code(productEntity.getCurrencyType());
		product.setCurrent_price(productPrice);
		
		return product;
	}
	
	public static ProductEntity convertProductToEntity (Product product) {
		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId(product.getId());
		productEntity.setAmount(product.getCurrent_price().getValue());
		productEntity.setCurrencyType(product.getCurrent_price().getCurrency_code());
		
		return productEntity;
	}

}
