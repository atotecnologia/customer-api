package br.com.composable.customerapi.model;

public enum CustomerSort {
	
	CUSTOMER_NAME("name"),	
	CUSTOMER_BIRTH_DATE("birth_date"),
	CUSTOMER_CREATED_AT("created_at"),
	ADDRESS_STATE("address_state"), 
	ADDRESS_CITY("address_city");
	
	private String title;
	
	private CustomerSort(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
