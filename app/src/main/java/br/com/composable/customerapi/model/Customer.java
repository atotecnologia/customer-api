package br.com.composable.customerapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import br.com.composable.customerapi.controller.form.CustomerForm;

public class Customer {
	
	private Long id;
	private String uuid;
	private String name;
	public String email;
	private LocalDate birthDate;
	private String cpf;
	private Gender gender;
	private List<Address> addresses;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	
	public Customer() {}
	
	public Customer(Long id) {
		this.id = id;
	}
	
	public Customer(CustomerForm form) {
		this.name = form.getName();
		this.email = form.getEmail();
		this.birthDate = form.getBirthDate();
		this.cpf = form.getCpf();
		this.gender = Gender.valueOf(form.getGender());
		this.uuid = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
		this.updateAt = LocalDateTime.now();
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
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ColumnName("birth_date")
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@ColumnName("created_at")
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@ColumnName("update_at")
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
}
