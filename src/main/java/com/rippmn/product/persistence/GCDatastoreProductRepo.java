package com.rippmn.product.persistence;

import org.springframework.stereotype.Repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

@Repository
public class GCDatastoreProductRepo implements ProductRepository {

	// private DatastoreService datastore;
	private static final String PRODUCT_NAME_KIND = "test";

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(PRODUCT_NAME_KIND);

	// public GCDatastoreProductRepo() {
	// datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized
	// Datastore service
	//
	//
	//
	// }

	// @Override
	// public Iterable<Entity> findByNameStartingWith(String name) {
	//
	// Query query = new Query(PRODUCT_NAME_KIND);
	// query.addProjection(new PropertyProjection("name", String.class));
	//
	// query.setFilter(CompositeFilterOperator.and(FilterOperator.GREATER_THAN_OR_EQUAL.of("name",
	// name), FilterOperator.LESS_THAN.of("name", name.concat("{"))));
	// return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	//
	// }
	//

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

	@Override
	public Iterable<Product> findByNameStartingWith(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createProduct(String name, String sku) {

		IncompleteKey key = keyFactory.newKey();

		FullEntity<IncompleteKey> productEntity = Entity.newBuilder(key).set("sku", sku).set("name", name).build();

		datastore.put(productEntity);
		return key.toString(); // The ID of the Key

	}

}
