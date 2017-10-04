package com.myRetail.repository;

import com.myRetail.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, Integer> {

	public ProductEntity findByProductId(Integer productId);
}
