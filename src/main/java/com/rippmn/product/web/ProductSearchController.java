package com.rippmn.product.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rippmn.product.persistence.ProductRepository;

@RestController
public class ProductSearchController {

	private static Logger log = LoggerFactory.getLogger(ProductSearchController.class);

	
	@Autowired
	ProductRepository productRepo;

	@RequestMapping("/")
	public String hello() {
		log.debug("returning homepage");
		return new Date().toString() + "-v_gae-std";
	}


//	@RequestMapping("/products")
//	public Iterable<Product> allProducts(){
//		return productRepo.findAll();
//	}

	@RequestMapping("/productAutoComplete")
	public Iterable<String> productAutoComplete(@RequestParam("startsWith") String startsWith){
		
		return productRepo.findByNameStartingWith(startsWith);
	}
	
	@RequestMapping("/createProd")
	public String createProduct() {
		
		return productRepo.createProduct(); 
	}

}
