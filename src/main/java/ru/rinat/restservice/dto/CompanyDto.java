package ru.rinat.restservice.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CompanyDto implements Serializable {

	private static final long serialVersionUID = 4895512615839920263L;

	private UUID id;
	private String name;
	private String phone;
	Set<AddressDto> addressies;


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
		CompanyDto other = (CompanyDto) obj;
		return Objects.equals(id, other.id) 
				&& Objects.equals(name, other.name) 
				&& Objects.equals(phone, other.phone);
	}

	public CompanyDto(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	
	public Set<AddressDto> getAddressies() {
		return addressies;
	}

	public void setAddressies(Set<AddressDto> addressies) {
		this.addressies = addressies;
	}

	public CompanyDto() {}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", phone=" + phone + "]";
	}
}
