package br.com.composable.customerapi.controller;

import org.eclipse.jetty.http.HttpStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import br.com.composable.customerapi.controller.dto.AddressDto;
import br.com.composable.customerapi.controller.form.AddressForm;
import br.com.composable.customerapi.model.ResponseModel;
import br.com.composable.customerapi.service.AddressService;
import br.com.composable.customerapi.transformer.JsonTransformer;
import spark.Request;
import spark.Response;

public class AddressController {
	
	private AddressService addressService;
	private JsonTransformer jsonTransformer;
	private Request request;
	private Response response;
	
	@Inject
	public AddressController(AddressService addressService, 
							  JsonTransformer jsonTransformer) {
		this.addressService = addressService;
		this.jsonTransformer = jsonTransformer;
	}
	
	public JsonNode create() {
		try {
			response.status(HttpStatus.CREATED_201);
			
			AddressDto addressDto = addressService
											.insertAddress(
													Long.valueOf(request.params(":id")),
													jsonTransformer
																.serialize(request.body(), AddressForm.class));
			
			return jsonTransformer
						.deserialize(addressDto);
			
		}catch (JsonParseException | MismatchedInputException mie) {
			
			response.status(HttpStatus.BAD_REQUEST_400);
			
			return jsonTransformer
						.deserialize(new ResponseModel("", ""));
			
		}catch (Exception e) {

			response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			
			return jsonTransformer
						.deserialize(new ResponseModel("create_address", e.getMessage()));
		}
	}
	
	public JsonNode update() {
		try {

			AddressDto addressDto = addressService
											.updateAddress(Long.valueOf(request.params(":id")),
														   Long.valueOf(request.params(":address_id")),
																	   jsonTransformer.serialize(request.body(), AddressForm.class));

			return jsonTransformer
						.deserialize(addressDto);
			
		}catch (RuntimeException re) {
			
			response.status(HttpStatus.NOT_FOUND_404);
			
			return jsonTransformer
						.deserialize(new ResponseModel("update_address", "Endereço não encontrato"));
			
		}catch (Exception e) {
			
			response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			
			return jsonTransformer
						.deserialize(new ResponseModel("update_address", "Erro interno do servidor"));
		}
	}
	
	public JsonNode remove() {
		try {
			
			response.status(HttpStatus.NO_CONTENT_204);
			
			addressService
				.removeAdress(Long.valueOf(request.params(":id")),
							  Long.valueOf(request.params(":address_id")));
		
			return jsonTransformer
						.deserialize(new ResponseModel("delete_address", "Address removido com sucesso"));
			
		}catch (Exception e) {
			
			response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			
			return jsonTransformer
						.deserialize(new ResponseModel("delete_address", "Erro interno do servidor"));
		}
}
	
	public JsonNode getAddresses(){
		try {
			return jsonTransformer
						.deserialize(addressService
											.summary(Long.valueOf(request.params(":id"))));

			
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
	
	public JsonNode getAddress(){
		try {
			return jsonTransformer
					.deserialize(addressService
									.retrieve(Long.valueOf(request.params(":id")), 
											Long.valueOf(request.params(":address_id"))));
			
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
	
	public void setRequest(Request request) {
		this.request = request;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
