package com.rippmn.product.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rippmn.product.persistence.Product;
import com.rippmn.product.persistence.ProductRepository;

@RestController
public class ProductSearchController {

	private static Logger log = LoggerFactory.getLogger(ProductSearchController.class);

	@Autowired
	ProductRepository productRepo;

	@RequestMapping("/health")
	public String hello() {
		log.debug("returning homepage");
		return new Date().toString() + "-v_gae-std-gcds";
	}

	@RequestMapping("/productAutoComplete")
	public Iterable<String> productAutoComplete(@RequestParam("startsWith") String startsWith){
		
		return productNameSearch(startsWith);
	}
	
	@RequestMapping("/products")
	public Iterable<Product> getProducts(){
		return productRepo.getProducts();
	}

	@RequestMapping("/productNames")
	public Iterable<String> productNameSearch(@RequestParam("term") String term){
		
		return productRepo.findByNameStartingWith(term);
	}

}
