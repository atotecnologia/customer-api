package br.com.composable.customerapi.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.composable.customerapi.model.Address;

public class AddressDto {
	
	private Long id;
	
	private String state;
	
	private String city;
	
	private String neighborhood;
	
	private String zipCode;
	
	private String street;
	
	private String number;
	
	private String additionalInformation;
	
	@JsonProperty("main")
	private boolean major;
	
	public AddressDto(Address address) {
		this.id = address.getId();
		this.state = address.getState();
		this.city = address.getCity();
		this.neighborhood = address.getNeighborhood();
		this.zipCode = address.getZipCode();
		this.street = address.getStreet();
		this.number = address.getNumber();
		this.additionalInformation = address.getAdditionalInformation();
		this.major = address.isMajor();
	}

	public Long getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getStreet() {
		return street;
	}


	public String getNumber() {
		return number;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public boolean isMajor() {
		return major;
	}


	public static AddressDto converter(Address address){
		return new AddressDto(address);
	}
	
	public static List<AddressDto> converter(List<Address> address){
		return address.stream()
					  .map(t-> converter(t))
					  .collect(Collectors.toList());
	}

}
