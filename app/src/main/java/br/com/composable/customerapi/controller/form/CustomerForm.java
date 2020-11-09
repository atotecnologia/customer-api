package br.com.composable.customerapi.controller.form;

import java.time.LocalDate;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import br.com.composable.customerapi.validator.Validatable;

public class CustomerForm implements Validatable{

	public String name;
	
	public String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate birthDate;

	private String cpf;
	
	private String gender;
	
	private AddressForm address;
	
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public AddressForm getAddress() {
		return address;
	}

	public void setAddress(AddressForm address) {
		this.address = address;
	}

	@Override
	public void validateDefault() {
		Validate.notBlank(name, "Nome do cliente e um campo obrigatorio");
		Validate.notBlank(cpf, "CPF do cliente e obrigatorio");
	}
	
}
