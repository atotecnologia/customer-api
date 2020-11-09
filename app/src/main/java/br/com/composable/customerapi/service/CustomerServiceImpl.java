package br.com.composable.customerapi.service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.inject.Inject;

import br.com.composable.customerapi.controller.dto.AddressDto;
import br.com.composable.customerapi.controller.dto.CustomerDto;
import br.com.composable.customerapi.controller.form.CustomerForm;
import br.com.composable.customerapi.dao.AddressDAO;
import br.com.composable.customerapi.dao.CustomerDAO;
import br.com.composable.customerapi.model.Address;
import br.com.composable.customerapi.model.Customer;
import br.com.composable.customerapi.validator.CustomerValidator;

public class CustomerServiceImpl implements CustomerService{
	
	private CustomerDAO customerDAO;
	private AddressDAO addressDAO;
	private CustomerValidator customerValidator;
	
	@Inject
	public CustomerServiceImpl(CustomerDAO customerDAO,
							   AddressDAO addressDAO,
							   CustomerValidator customerValidator) {
		this.customerDAO = customerDAO;
		this.addressDAO = addressDAO;
		this.customerValidator = customerValidator;
	}

	@Override
	public List<CustomerDto> summary(Map<String, Object> filter, 
								     String orderBy,
								     String order) {
		
		List<Customer> customers = customerDAO
									.summary(filter, orderBy, order);
		customers.forEach(c->{
			c.setAddresses(addressDAO.summary(c.getId()));
		});
		
		if(customers.isEmpty()) {
			throw new RuntimeException("Cliente nao encontrado.");
		}
		
		return CustomerDto.converter(customers);
	}

	@Override
	public CustomerDto insertCustomer(CustomerForm form) {
			
		customerValidator.validate(form);

		Customer customer = new Customer(form);
		Address address = new Address(form.getAddress());
		
		Long idCustomer = insert(customer, address);
		
		customer.setId(idCustomer);
		
		return makeCustomerDto(customer);
					
	}

	@Override
	public CustomerDto updateCustomer(Long id, CustomerForm form)  {
			
		Customer customer = new Customer(form);
		customer.setId(id);
		Address address = null;
		
		if(form.getAddress()!=null) {
			form.getAddress().setCustomerId(id);
			address = new Address(form.getAddress());
		}

		customerValidator.validate(id, form);

		update(customer, address);
		
		return makeCustomerDto(customer);				
	}

	

	@Override
	public CustomerDto retrieve(Long id) {

		return makeCustomerDto(customerDAO
									.retrieve(id));
	}

	@Override
	public void removeCustomer(Long idCustomer) throws Exception {
		try {

			customerDAO.begin();
			addressDAO.begin();

			addressDAO.deleteByCustomer(idCustomer);
			customerDAO.delete(idCustomer);

			addressDAO.commit();
			customerDAO.commit();

		}catch (Exception e) {
			addressDAO.rollback();
			customerDAO.rollback();
			throw e;
		}
	}

	private CustomerDto makeCustomerDto(Customer customer) {
		
		if(Optional.ofNullable(customer)
				.isPresent()){
			
			CustomerDto customerDto = new CustomerDto(customer);
			customerDto.populateAddressesDto(AddressDto
												.converter(addressDAO
														.summary(customer.getId())));
			
			return customerDto;
		}
		throw new RuntimeException("Cliente não encontrado");		
	}
	
	private Long insert(Customer customer,
			Address address) {
		try {
			
			customerDAO.begin();
			addressDAO.begin();
			
			Long idCustomer = customerDAO.insert(customer);
			
			address.setCustomerId(idCustomer);
			addressDAO.insert(address);

			customerDAO.commit();
			addressDAO.commit();

			return idCustomer;
			
		}catch (Exception e) {
			customerDAO.rollback();
			addressDAO.rollback();
			throw e;
		}
		
	}
	
	private void update(Customer customer,
			Address address) {
		try {
			
			customerDAO.begin();
			addressDAO.begin();
			
			customerDAO.update(customer);
			
			if(address!=null) {
				addressDAO.updateByCustomer(address);	
			}
			
			customerDAO.commit();
			addressDAO.commit();
			
		}catch (Exception e) {
			customerDAO.rollback();
			addressDAO.rollback();
			throw e;
		}
		
	}	
}
