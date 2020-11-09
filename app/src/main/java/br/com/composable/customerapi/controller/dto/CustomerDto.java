package br.com.composable.customerapi.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import br.com.composable.customerapi.model.Customer;
import br.com.composable.customerapi.model.Gender;

@JsonPropertyOrder({ "id", "uuid", "name", "email", 
					 "birthDate", "cpf", "gender", "mainAddress",
					 "adresses", "createdAt", "updateAt"})
public class CustomerDto {
	
	private Long id;
	
	private String uuid;
	
	private String name;
	
	private String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)	
	private LocalDate birthDate;
	
	private String cpf;
	
	private Gender gender;
	
	private AddressDto mainAddress;
	
	@JsonProperty("adresses")
	private List<AddressDto> addressesDto;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)	
	private LocalDateTime createdAt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)	
	private LocalDateTime updateAt;
	
	public CustomerDto() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerDto(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.email = customer.getEmail();
		this.birthDate = customer.getBirthDate();
		this.cpf = customer.getCpf();
		this.gender = customer.getGender();
		this.uuid = customer.getUuid();
		this.createdAt = customer.getCreatedAt();
		this.updateAt = customer.getUpdateAt();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}


	public String getCpf() {
		return cpf;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<AddressDto> getAddressesDto() {
		return addressesDto;
	}
	
	public AddressDto getMainAddress() {
		return mainAddress;
	}
	
	public String getUuid() {
		return uuid;
	}


	public String getEmail() {
		return email;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public LocalDateTime getUpdateAt() {
		return updateAt;
	}


	public void populateAddressesDto(List<AddressDto> addressesDto) {
		this.addressesDto = addressesDto;
		populateMainAddress();
	}
	
	private void populateMainAddress() {
		addressesDto.forEach(ad->{
			if(ad.isMajor()) {
				mainAddress = ad;
			}
		});
	}

	public static CustomerDto converter(Customer customer){
		CustomerDto customerDto = new CustomerDto(customer);
		customerDto.populateAddressesDto(AddressDto.converter(customer.getAddresses()));
		return customerDto;
	}
	
	public static List<CustomerDto> converter(List<Customer> customers){
		return customers.stream()
					  .map(t-> converter(t))
					  .collect(Collectors.toList());
	}

}
