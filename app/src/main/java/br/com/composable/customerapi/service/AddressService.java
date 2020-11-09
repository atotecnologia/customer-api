package br.com.composable.customerapi.service;

import java.util.List;

import br.com.composable.customerapi.controller.dto.AddressDto;
import br.com.composable.customerapi.controller.form.AddressForm;

public interface AddressService {
	
	public AddressDto insertAddress(Long idCustomer, AddressForm form) throws Exception ;
	public AddressDto updateAddress(Long idCustomer, Long idAddress, AddressForm form) throws Exception ;
	public void removeAdress(Long idCustomer, Long idAddress) throws Exception ;
	public List<AddressDto> summary(Long idCustomer);
	public AddressDto retrieve(Long idCustomer, Long idAddress);

}
