package br.com.composable.customerapi.service;

import java.util.List;
import java.util.Map;

import br.com.composable.customerapi.controller.dto.CustomerDto;
import br.com.composable.customerapi.controller.form.CustomerForm;

public interface CustomerService {
	
	public CustomerDto insertCustomer(CustomerForm form) throws Exception ;
	public CustomerDto updateCustomer(Long id, CustomerForm form) throws Exception ;
	public void removeCustomer(Long id) throws Exception ;
	public List<CustomerDto> summary(Map<String, Object> filter, String orderBy, String order);
	public CustomerDto retrieve(Long id);

}
