package com.rippmn.product.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;

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
	public Iterable<String> findByNameStartingWith(String name) {
		
		Query query = new Query(PRODUCT_NAME_KIND);
		query.addProjection(new PropertyProjection("name", String.class));
		List<Entity> productNames = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		ArrayList<String> names = new ArrayList<String>(productNames.size());
		
		for(Entity result:productNames) {
			names.add((String)result.getProperty("name"));
		}
		
		return names;
	}

	@Override
	public String createProduct() {
		
		Entity incProduct = new Entity(PRODUCT_NAME_KIND);  // Key will be assigned once written
		incProduct.setProperty("sku", Integer.toString(prodCount));
		incProduct.setProperty("name", "prod " + prodCount);

	    Key prodKey = datastore.put(incProduct); // Save the Entity
	    return Long.toString(prodKey.getId());                     // The ID of the Key
	    
	}
	
	

}
