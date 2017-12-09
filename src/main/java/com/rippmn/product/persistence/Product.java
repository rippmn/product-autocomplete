package com.rippmn.product.persistence;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.Value;

public class Product {
	private String sku;
	private String name;
	private List<String> tags;
	
	
	public Product(String sku, String name, List<Value<String>> tags) {
		super();
		this.sku = sku;
		this.name = name;
		
		
		this.tags = new ArrayList<String>(tags.size());
		for(Value<String> v:tags) {
			this.tags.add(v.get());
		}
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTags() {
		return this.tags;
	}
	
	
	
	
}