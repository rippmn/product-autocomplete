package com.rippmn.product.persistence;

import org.springframework.data.repository.CrudRepository;

import com.rippmn.product.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	public Iterable<Product> findByNameStartingWith(String name);
	
}
