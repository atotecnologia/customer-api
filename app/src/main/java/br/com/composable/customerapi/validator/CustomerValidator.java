package br.com.composable.customerapi.validator;

import java.time.LocalDate;
import java.time.Period;

import com.google.inject.Inject;

import br.com.composable.customerapi.controller.form.CustomerForm;
import br.com.composable.customerapi.dao.AddressDAO;
import br.com.composable.customerapi.dao.CustomerDAO;
import br.com.composable.customerapi.model.Address;
import br.com.composable.customerapi.model.Customer;

public class CustomerValidator {
	
	private CustomerDAO customerDAO;
	private AddressDAO addressDAO;
	
	@Inject
	public CustomerValidator(CustomerDAO customerDAO,
							 AddressDAO addressDAO) {
		this.customerDAO = customerDAO;
		this.addressDAO = addressDAO;
	}
	
	public void validate(CustomerForm form)  {
		validate(null, form);
	}
	public void validate(Long idCustomer, 
			             CustomerForm form)  {
		
		form.validateDefault();
		
		Customer customer = customerDAO
								.retrieveByCpf(form.getCpf());
		
		if(idCustomer == null &&
		   customer != null && 
		   !customer.getId().equals(idCustomer)){
		
			throw new RuntimeException("Ja existe um cliente cadastrado o CPF informado");
		}
		
		if(form.getBirthDate() != null) {
			if(Period.between(form.getBirthDate(), LocalDate.now())
			    .getYears()>100) {
				
				throw new RuntimeException("O cliente nao pode ter mais que 100 anos");	
			}
		}
		
		if(form.getAddress() != null && 
			form.getAddress().getCustomerId()!=null) {
			
			Address address = addressDAO
									.retrieveByCustomer(form.getAddress().getCustomerId());
		
			if(!form.getAddress().isMajor() &&
			   !address.isMajor()) {	
				
				throw new RuntimeException("O cliente precisa ter ao menos um endereco principal");
						
			}
		
		}
	}
}
