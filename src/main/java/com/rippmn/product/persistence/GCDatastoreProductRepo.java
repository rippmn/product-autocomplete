package com.rippmn.product.persistence;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Repository
public class GCDatastoreProductRepo implements ProductRepository {

	// [START constructor]
	private DatastoreService datastore;
	private static final String PRODUCT_NAME_KIND = "test";

	private int prodCount=0;
	
	public GCDatastoreProductRepo() {
	    datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
	  }

	@Override
	public Iterable<Entity> findByNameStartingWith(String name) {
		
		Query query = new Query(PRODUCT_NAME_KIND);
		query.addProjection(new PropertyProjection("name", String.class));
		query.addFilter("name", FilterOperator.GREATER_THAN_OR_EQUAL, name).addFilter("name", FilterOperator.LESS_THAN, name+"{");
		return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
	}
	
	//public Iterable<Entity> listProd

	@Override
	public String createProduct(String name, String sku) {
		
		Entity incProduct = new Entity(PRODUCT_NAME_KIND);  // Key will be assigned once written
		incProduct.setProperty("sku", sku);
		incProduct.setProperty("name", name);

	    Key prodKey = datastore.put(incProduct); // Save the Entity
	    return Long.toString(prodKey.getId());                     // The ID of the Key
	    
	}
	
	

}
