package br.com.composable.customerapi.infra.config;


import org.jdbi.v3.core.Jdbi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import br.com.composable.customerapi.CustomerApiApplication;
import br.com.composable.customerapi.controller.AddressController;
import br.com.composable.customerapi.controller.CustomerController;
import br.com.composable.customerapi.dao.AddressDAO;
import br.com.composable.customerapi.dao.CustomerDAO;
import br.com.composable.customerapi.service.AddressService;
import br.com.composable.customerapi.service.AddressServiceImpl;
import br.com.composable.customerapi.service.CustomerService;
import br.com.composable.customerapi.service.CustomerServiceImpl;
import br.com.composable.customerapi.transformer.JsonTransformer;
import br.com.composable.customerapi.validator.CustomerValidator;

public class AppModule extends AbstractModule {
	
	private Jdbi jdbi;
	
	public AppModule() {
		jdbi = DataSource.getJdbi();
	}
	
	@Override
	protected void configure() {
		bind(DataSource.class);
		
		bind(CustomerApiApplication.class);
		
		bind(CustomerController.class);
		bind(CustomerService.class).to(CustomerServiceImpl.class);
		bind(CustomerValidator.class);
		
		bind(AddressController.class);
		bind(AddressService.class).to(AddressServiceImpl.class);
	}

	@Provides 
	@Singleton
	public JsonTransformer getJsonTransformer() {
	    return new JsonTransformer(new ObjectMapper());
	}

	@Provides
	public CustomerDAO getCustomerDAO() {
		return jdbi
				.onDemand(CustomerDAO.class);
	}
	
	@Provides
	public AddressDAO getAddressDAO() {
		return jdbi
				.onDemand(AddressDAO.class);
	}

}
