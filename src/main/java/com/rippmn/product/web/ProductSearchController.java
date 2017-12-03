package com.rippmn.product.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductSearchController {
	
	private static Logger log = LoggerFactory.getLogger(ProductSearchController.class); 
	
	@RequestMapping("/")
	public String hello() {
		log.debug("returning homepage");
		return new Date().toString();
	}

}
