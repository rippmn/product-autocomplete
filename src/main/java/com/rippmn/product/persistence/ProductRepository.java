package com.rippmn.product.persistence;

public interface ProductRepository{ 

	public Iterable<Product> getProducts();
	
	public Iterable<String> findByNameStartingWith(String name);
	
}
