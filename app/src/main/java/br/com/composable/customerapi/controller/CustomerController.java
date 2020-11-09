package br.com.composable.customerapi.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import br.com.composable.customerapi.controller.dto.CustomerDto;
import br.com.composable.customerapi.controller.form.CustomerForm;
import br.com.composable.customerapi.model.CustomerSort;
import br.com.composable.customerapi.model.ResponseModel;
import br.com.composable.customerapi.service.CustomerService;
import br.com.composable.customerapi.transformer.JsonTransformer;
import spark.Request;
import spark.Response;

public class CustomerController {
	
	private CustomerService customerService;
	private JsonTransformer jsonTransformer;
	private Request request;
	private Response response;
	
	@Inject
	public CustomerController(CustomerService customerService, 
							  JsonTransformer jsonTransformer) {
		this.customerService = customerService;
		this.jsonTransformer = jsonTransformer;
	}
	
	public JsonNode getCustomers(){
		try {
			return jsonTransformer
					.deserialize(
							customerService
								.summary(getFilter(request),
										getSortBy(request),
										getSortOrder(request)));
			
		}catch (RuntimeException re) {

			response.status(HttpStatus.NO_CONTENT_204);
			
			return jsonTransformer
					.deserialize(new ResponseModel("", ""));
			
		}catch (Exception e) {

			response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			
			return jsonTransformer
					.deserialize(new ResponseModel("", ""));
		}
	}
	
	public JsonNode getCustomer() {
		try {
			
			return jsonTransformer
						.deserialize(customerService
										.retrieve(Long.valueOf(request.params(":id"))));

		}catch (RuntimeException re) {

			response.status(HttpStatus.NOT_FOUND_404);
			
			return jsonTransformer
					.deserialize(new ResponseModel("", ""));
			
		}catch (Exception e) {

			response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			
			return jsonTransformer
					.deserialize(new ResponseModel("", ""));
		}
	}
	
	public JsonNode create() {
		try {
			response.status(HttpStatus.CREATED_201);
			
			CustomerDto customerDto = customerService
											.insertCustomer(jsonTransformer
																	.serialize(request.body(), CustomerForm.class));
			
			return jsonTransformer
						.deserialize(customerDto);
			
		}catch (JsonParseException | MismatchedInputException mie) {
			
			response.status(HttpStatus.BAD_REQUEST_400);
			
			return jsonTransformer
						.deserialize(new ResponseModel("", ""));
			
		}catch (Exception e) {

			response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			
			return jsonTransformer
						.deserialize(new ResponseModel("create_customer", e.getMessage()));
		}
	}
	
	public JsonNode update() {
			try {
				
				CustomerDto customerDto = customerService
												.updateCustomer(Long.valueOf(request.params(":id")),
																		   jsonTransformer.serialize(request.body(), CustomerForm.class));

				return jsonTransformer
							.deserialize(customerDto);
				
			}catch (RuntimeException re) {
				
				response.status(HttpStatus.NOT_FOUND_404);
				
				return jsonTransformer
							.deserialize(new ResponseModel("update_customer", "Customer nao encontrato"));
				
			}catch (Exception e) {
				
				response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				
				return jsonTransformer
							.deserialize(new ResponseModel("update_customer", "Erro interno do servidor"));
			}
	}

	public JsonNode remove() {
			try {
				
				response.status(HttpStatus.NO_CONTENT_204);
				
				customerService
					.removeCustomer(Long.valueOf(request.params(":id")));
			
				return jsonTransformer
							.deserialize(new ResponseModel("delete_customer", "Customer removido com sucesso"));
				
			}catch (Exception e) {
				
				response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				
				return jsonTransformer
							.deserialize(new ResponseModel("update_customer", "Erro interno do servidor"));
			}
	}

	private Map<String, Object> getFilter(Request request){
		Map<String, Object> filter = new HashMap<String, Object>();
		
		filter.put("name", request.queryParams("name"));
		if(!StringUtils.isEmpty(request.queryParams("birthDate"))) {
			filter.put("birthDate", LocalDate.parse(request.queryParams("birthDate")));				
		}else {
			filter.put("birthDate", null);
		}
		filter.put("state", request.queryParams("state"));
		filter.put("city", request.queryParams("city"));

		return filter;
	}
	
	private String getSortBy(Request request) {
		if(StringUtils.isEmpty(request.queryParams("sortBy"))) {
			return CustomerSort.CUSTOMER_NAME.getTitle();
		}
		
		return CustomerSort
				.valueOf(request.queryParams("sortBy"))
				.getTitle();
		
	}
	
	private String getSortOrder(Request request) {
		if(StringUtils.isEmpty(request.queryParams("sortOrder"))) {
			return "ASC";
		}
		
		return request.queryParams("sortOrder");
		
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
}
