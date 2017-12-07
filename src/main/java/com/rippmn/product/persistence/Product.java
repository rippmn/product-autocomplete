package com.rippmn.product.persistence;

public class Product {
	private String sku;
	private String name;
	
	public Product(String sku, String name) {
		super();
		this.sku = sku;
		this.name = name;
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
	
	
}