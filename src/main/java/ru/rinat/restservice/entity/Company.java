package ru.rinat.restservice.entity;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "companies")
public class Company {
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	@Column(name = "ID", length = 36)
	private UUID id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "phone", unique = true, nullable = false)
	private String phone;
	
	@ManyToMany
	@JoinTable(
			  name = "companies_addresses", 
			  joinColumns = @JoinColumn(name = "companies_ID"), 
			  inverseJoinColumns = @JoinColumn(name = "addresses_ID"))
	Set<Address> addressies;


	public Set<Address> getAddressies() {
		return addressies;
	}

	public void setAddressies(Set<Address> addressies) {
		this.addressies = addressies;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
	}

	public Company(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	
	public Company(){}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", phone=" + phone + "]";
	}
}
