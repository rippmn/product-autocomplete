package com.rippmn.product.persistence;

public interface ProductRepository{ 

	public Iterable<String> findByNameStartingWith(String name);
	
	public String createProduct();
	
}
