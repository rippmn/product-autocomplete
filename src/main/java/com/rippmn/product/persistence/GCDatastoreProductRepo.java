package com.rippmn.product.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.ProjectionEntity;
import com.google.cloud.datastore.ProjectionEntityQuery;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

@Repository
public class GCDatastoreProductRepo implements ProductRepository {

	private static final Logger logger = LoggerFactory.getLogger(GCDatastoreProductRepo.class);
	
	// private DatastoreService datastore;
	private static final String PRODUCT_NAME_KIND = "test";

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	@Override
	public Iterable<String> findByNameStartingWith(String name) {

		logger.info("retrieving product names containing " + name);
		
		ProjectionEntityQuery projectionQuery = Query.newProjectionEntityQueryBuilder().setKind(PRODUCT_NAME_KIND)
				.setProjection("name")
				//need to filter
				.setFilter(CompositeFilter.and(PropertyFilter.ge("name", name), PropertyFilter.lt("name", name.concat("{"))))
				.build();
		
		QueryResults<ProjectionEntity> productNames = datastore.run(projectionQuery);
		
		Builder<String> names = ImmutableList.builder();
		
		while(productNames.hasNext()) {
			ProjectionEntity p = productNames.next();
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

			products.add(new Product(prod.getString("sku"), prod.getString("name")));

		}
		return products.build();
	}
	
}
