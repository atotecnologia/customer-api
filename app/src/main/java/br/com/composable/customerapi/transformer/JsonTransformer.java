package br.com.composable.customerapi.transformer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
	
	private ObjectMapper objectMapper;
	
	public JsonTransformer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public String render(Object model) throws Exception {
		return objectMapper
					.writeValueAsString(model);
	}
	
	public <T> T serialize(String body, Class<T> clazz) throws Exception {
		try {
			return objectMapper
					.readValue(body, clazz);		
		}catch (Exception e) {
			throw e;
		}
		
	}
	
	public JsonNode deserialize(Object object) {
		try {
			return  objectMapper
						.convertValue(object, JsonNode.class);
		}catch (Exception e) { 
			throw e;
		}
		
	}

}
