package com.rippmn.product.persistence;

import com.google.appengine.api.datastore.Entity;

public interface ProductRepository{ 

	public Iterable<Entity> findByNameStartingWith(String name);
	
	public String createProduct(String name, String sku);
	
}
