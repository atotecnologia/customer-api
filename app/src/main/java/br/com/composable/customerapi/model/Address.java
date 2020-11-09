package br.com.composable.customerapi.model;

import java.util.Optional;

import br.com.composable.customerapi.controller.form.AddressForm;

public class Address {
	
	private Long id;
	private Long customerId;
	private String city;
	private String neighborhood;
	private String zipCode;
	private String street;
	private String number;
	private String additionalInformation;
	private String state;
	private boolean major;
	
	public Address() {}
	
	public Address(AddressForm form) {
		this(null, form);
	}
	
	public Address(Long idCustomer, AddressForm form) {
		Long customerId = Optional.ofNullable(idCustomer).isPresent() ? idCustomer : form.getCustomerId();
		this.customerId = customerId;
		this.city = form.getCity();
		this.neighborhood = form.getNeighborhood();
		this.zipCode = form.getZipCode();
		this.street = form.getStreet();
		this.number = form.getNumber();
		this.additionalInformation = form.getAdditionalInformation();
		this.state = form.getState();
		this.major = form.isMajor();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isMajor() {
		return major;
	}

	public void setMajor(boolean major) {
		this.major = major;
	}
	
	
}
