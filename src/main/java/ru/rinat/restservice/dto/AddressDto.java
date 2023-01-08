package ru.rinat.restservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


public class AddressDto implements Serializable {
	private static final long serialVersionUID = 904146912865711857L;
	
	private UUID id;
	private String city;
	private String street;
	private String houseNumber;
	private Integer flatNumber;
	Set<CompanyDto> companies;
	
	public AddressDto() {}
		
	public AddressDto(String city, String street, String houseNumber, Integer flatNumber) {
		super();
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.flatNumber = flatNumber;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(city, houseNumber, id, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDto other = (AddressDto) obj;
		return Objects.equals(city, other.city) && Objects.equals(houseNumber, other.houseNumber)
				&& Objects.equals(id, other.id) && Objects.equals(street, other.street);
	}
	
	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", street=" + street + ", houseNumber=" + houseNumber
				+ ", flatNumber=" + flatNumber + "]";
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(Integer flatNumber) {
		this.flatNumber = flatNumber;
	}
}
