package com.rippmn.product.web;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductSearchController {
	
	@RequestMapping("/")
	public String hello() {
		return new Date().toString();
	}

}
