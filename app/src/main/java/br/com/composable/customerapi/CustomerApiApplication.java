package br.com.composable.customerapi;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.inject.Inject;

import br.com.composable.customerapi.controller.AddressController;
import br.com.composable.customerapi.controller.CustomerController;
import br.com.composable.customerapi.transformer.JsonTransformer;

public class CustomerApiApplication {
	
	private CustomerController customerController;
	private AddressController addressController;
	private JsonTransformer jsonTransformer;

	@Inject
	public CustomerApiApplication(CustomerController customerController, 
								  AddressController addressController,
							      JsonTransformer jsonTransformer) {
		this.customerController = customerController;
		this.addressController = addressController;
		this.jsonTransformer = jsonTransformer;
	}

	public void initialize() {
		port(8081);
		
		before((request, response) -> {
			response.type("application/json");			
			
			customerController.setRequest(request);
			customerController.setResponse(response);
			
			addressController.setRequest(request);
			addressController.setResponse(response);
			
		});
		
		post("/customers", (request, response) -> {

		    return customerController.create();
		    
		}, jsonTransformer);

		get("/customers", (request, response) -> {
		    
		    return customerController.getCustomers();
		    
		}, jsonTransformer);
		
		get("/customers/:id", (request, response) -> {
		    
		    return customerController.getCustomer();
		    
		}, jsonTransformer);
		
		put("/customers/:id", (request, response) -> {
		    
		    return customerController.update();
		    
		}, jsonTransformer);
		
		delete("/customers/:id", (request, response) -> {
		    
		    return customerController.remove();
		    
		}, jsonTransformer);
		
		post("/customers/:id/addresses", (request, response) -> {

		    return addressController.create();
		    
		}, jsonTransformer);
		
		get("/customers/:id/addresses", (request, response) -> {
		    
		    return addressController.getAddresses();
		    
		}, jsonTransformer);
		
		get("/customers/:id/addresses/:address_id", (request, response) -> {
		    
		    return addressController.getAddress();
		    
		}, jsonTransformer);
		
		put("/customers/:id/addresses/:address_id", (request, response) -> {
		    
		    return addressController.update();
		    
		}, jsonTransformer);
		
		delete("/customers/:id/addresses/:address_id", (request, response) -> {
		    
		    return addressController.remove();
		    
		}, jsonTransformer);
		
		
	}
	


}
