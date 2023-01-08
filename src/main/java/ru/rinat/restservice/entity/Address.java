package ru.rinat.restservice.entity;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "addresses")
public class Address {
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	@Column(name = "ID", length = 36)
	private UUID id;
	
	public Set<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}

	@Column(name = "city", unique = true, nullable = false)
	private String city;
	
	@Column(name = "street", nullable = false)
	private String street;
	
	@Column(name = "house_number", nullable = false)
	private String houseNumber;
	
	@Column(name = "flat_number")
	private Integer flatNumber;	
	
	@ManyToMany(mappedBy = "addressies")
	Set<Company> companies;

	public Address() {}
		
	public Address(String city, String street, String houseNumber, Integer flatNumber) {
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
		Address other = (Address) obj;
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
