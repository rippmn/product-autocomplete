package com.rippmn.product.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

@Repository
public class GCDatastoreProductRepo implements ProductRepository {

	private static final Logger logger = LoggerFactory.getLogger(GCDatastoreProductRepo.class);
	
	//TODO Convert this to a environment variable
	private static final String PRODUCT_NAME_KIND = "productNames";

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	@Override
	public Iterable<String> findByNameStartingWith(String name) {
		
		String lowerName = name.toLowerCase();
		logger.info("retrieving product names containing " + name);
		
		EntityQuery prodQuery = Query.newEntityQueryBuilder().setKind(PRODUCT_NAME_KIND)
				.setFilter(CompositeFilter.and(PropertyFilter.ge("tag", lowerName), PropertyFilter.lt("tag", lowerName.concat("{"))))
				.build();
				
		QueryResults<Entity> results = datastore.run(prodQuery);
		
		Builder<String> names = ImmutableList.builder();
		
		while(results.hasNext()) {
			Entity p = results.next();
			names.add(p.getString("name"));
		}

		return names.build();
	}

	public Iterable<Product> getProducts() {

		// for now setting this to limit of 15 as local dataset should be inside that
		// range
		EntityQuery prodQuery = Query.newEntityQueryBuilder().setKind(PRODUCT_NAME_KIND).setLimit(15).build();

		QueryResults<Entity> prods = datastore.run(prodQuery);

		Builder<Product> products = ImmutableList.builder();

		while (prods.hasNext()) {
			Entity prod = prods.next();
			
			products.add(new Product(prod.getKey().getName(), prod.getString("name"), prod.getList("tag")));

		}
		return products.build();
	}
	
	
	
}
