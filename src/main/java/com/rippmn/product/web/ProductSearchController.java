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

	static final String[][] products = new String[][] 
		{{"43900","Duracell - AAA Batteries (4-Pack)"},{"48530","Duracell - AA 1.5V CopperTop Batteries (4-Pack)"},{"127687","Duracell - AA Batteries (8-Pack)"},{"150115","Energizer - MAX Batteries AA (4-Pack)"},{"185230","Duracell - C Batteries (4-Pack)"},{"185267","Duracell - D Batteries (4-Pack)"},{"312290","Duracell - 9V Batteries (2-Pack)"},{"324884","Directed Electronics - Viper Audio Glass Break Sensor"},{"333179","Energizer - N Cell E90 Batteries (2-Pack)"},{"346575","Metra - Radio Installation Dash Kit for Most 1989-2000 Ford, Lincoln & Mercury Vehicles - Black"},{"346646","Metra - Radio Dash Multikit for Select GM Vehicles - Black"}};
		
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
