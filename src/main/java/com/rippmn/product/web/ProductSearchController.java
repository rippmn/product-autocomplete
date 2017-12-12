package com.rippmn.product.web;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.rippmn.product.persistence.Product;
import com.rippmn.product.persistence.ProductRepository;

@RestController
public class ProductSearchController {

	private static Logger log = LoggerFactory.getLogger(ProductSearchController.class);

	@Autowired
	ProductRepository productRepo;

	private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
	private static final Expiration expiration = Expiration.byDeltaSeconds(3600);
	private boolean cacheCheck = true;

	@PostConstruct()
	private void initializeCache() {

		if (System.getenv().get("NO_CACHE") != null && System.getenv().get("NO_CACHE").equals("yes")) {
			log.info("Initializing Cache to false");
			cacheCheck = false;
		}

	}

	@RequestMapping("/health")
	public String hello() {
		log.debug("returning homepage");
		return new Date().toString() + "-v_gae-std-gcds";
	}

	@RequestMapping("/productAutoComplete")
	public Iterable<String> productAutoComplete(@RequestParam("startsWith") String startsWith) {

		return productNameSearch(startsWith);
	}

	@RequestMapping("/products")
	public Iterable<Product> getProducts() {
		return productRepo.getProducts();
	}

	@RequestMapping("/productNames")
	public Iterable<String> productNameSearch(@RequestParam("term") String term) {

		Iterable<String> prodNames;
		
		if (cacheCheck) {
			prodNames = (Iterable<String>) cache.get(term);
			if (prodNames == null) {
				log.info("cache hit for term " + term);
				prodNames = productRepo.findByNameStartingWith(term);
				if (prodNames != null && prodNames.iterator().hasNext()) {
					cache.put(term, prodNames, expiration);
				}
				
			}
		} else {
			prodNames = productRepo.findByNameStartingWith(term);
		}
		return prodNames;

	}

}
