package com.rippmn.product.web;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rippmn.product.domain.Product;
import com.rippmn.product.persistence.ProductRepository;

@RestController
public class ProductSearchController {

	private static Logger log = LoggerFactory.getLogger(ProductSearchController.class);

	@Autowired
	ProductRepository productRepo;
	
	@RequestMapping("/health")
	public String hello() {
		log.debug("returning homepage");
		return new Date().toString() + "-v_gaeflex";
	}


	@RequestMapping("/products")
	public Iterable<Product> allProducts(){
		return productRepo.findAll();
	}

	@RequestMapping("/productAutoComplete")
	public Iterable<Product> productAutoComplete(@RequestParam("startsWith") String startsWith){
		return productRepo.findByNameStartingWith(startsWith);
	}

	@RequestMapping("/productNames")
	public Iterable<String> productNameSearch(@RequestParam("term") String term){
		ArrayList<String> names = new ArrayList<String>();

		for(Product p:productRepo.findByNameStartingWith(term)) {
			names.add(p.getName());
		}

		return names;
	}

}
