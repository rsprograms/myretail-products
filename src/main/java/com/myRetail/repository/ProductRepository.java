package com.myRetail.repository;

import com.myRetail.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntity, Integer> {

	public ProductEntity findByProductId(Integer productId);
}
