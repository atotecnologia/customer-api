package br.com.composable.customerapi.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressForm {
	
	private Long customerId;
	
	private String state;
	
	private String city;
	
	private String neighborhood;
	
	private String zipCode;
	
	private String street;
	
	private String number;
	
	private String additionalInformation;
	
	@JsonProperty("main")
	private boolean major;
	
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public boolean isMajor() {
		return major;
	}
	public void setMajor(boolean major) {
		this.major = major;
	}

}
