package br.com.composable.customerapi.service;

import java.util.List;
import java.util.Optional;

import com.google.inject.Inject;

import br.com.composable.customerapi.controller.dto.AddressDto;
import br.com.composable.customerapi.controller.form.AddressForm;
import br.com.composable.customerapi.dao.AddressDAO;
import br.com.composable.customerapi.model.Address;


public class AddressServiceImpl implements AddressService{
	
	private AddressDAO addressDAO;

	@Inject
	public AddressServiceImpl(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public AddressDto insertAddress(Long idCustomer, 
								    AddressForm form) throws Exception {
		
		return makeAddressDto(insert(new Address(idCustomer, form)));
	}

	@Override
	public AddressDto updateAddress(Long idCustomer, 
									Long idAddress, 
									AddressForm form) throws Exception {
		
		Address address = new Address(idCustomer, form);
		
		if(retrieve(idCustomer, idAddress) == null) {
			throw new RuntimeException("Endereço não encontrado");
		}
		
		update(idCustomer, idAddress, address);
		
		address.setId(idAddress);

		return makeAddressDto(address);
	}

	@Override
	public void removeAdress(Long idCustomer, 
							 Long idAddress) throws Exception {
		try {

			addressDAO.begin();
			
			addressDAO.delete(idCustomer, idAddress);

			addressDAO.commit();

		}catch (Exception e) {
			addressDAO.rollback();
			throw e;
		}
		
	}

	@Override
	public List<AddressDto> summary(Long idCustomer) {
		
		List<Address> addresses = addressDAO
									.summary(idCustomer);
		
		if(addresses.isEmpty()) {
			throw new RuntimeException("Endereço nao encontrado.");
		}
		
		return AddressDto.converter(addresses);
	}

	@Override
	public AddressDto retrieve(Long idCustomer, Long idAddress) {
		return makeAddressDto(addressDAO
									.retrieveByCustomerAddress(idCustomer, idAddress));
	}
	
	private Address insert(Address address) {
		try {

			addressDAO.begin();
			
			updateMajorAddressToCustomer(address);
			
			address.setId(addressDAO.insert(address));

			addressDAO.commit();

			return address;
			
		}catch (Exception e) {
			addressDAO.rollback();
			throw e;
		}
		
	}
	
	private void update(Long idCustomer, 
					    Long idAddress,
					    Address address) {
		try {
			
			addressDAO.begin();
			
			addressDAO.update(idCustomer, idAddress, address);
			
			addressDAO.commit();
			
		}catch (Exception e) {
			addressDAO.rollback();
			throw e;
		}
		
	}
	
	private void updateMajorAddressToCustomer(Address address) {
		if(address.isMajor()) {
			address.setMajor(false);
			addressDAO.updateByCustomer(address);
			address.setMajor(true);
		}
	}
	
	private AddressDto makeAddressDto(Address address) {
		
		
		if(Optional.ofNullable(address)
				.isPresent()){
			
			AddressDto addressDto = new AddressDto(address);
			AddressDto.converter(addressDAO
									.summary(address.getId()));
			
			return addressDto;
		}
		throw new RuntimeException("Endereço não encontrado");		
	}

}
