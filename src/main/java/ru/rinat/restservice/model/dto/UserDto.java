package ru.rinat.restservice.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -345819662589854161L;
	
	private UUID id;
	private String firstName;
	private String lastName;
	private LocalDateTime dateTimeDeleted;
	private String login;

	public UserDto() {
	}

	public UserDto(String firstName, String lastName, String login) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTimeDeleted, id, login);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(dateTimeDeleted, other.dateTimeDeleted) && Objects.equals(id, other.id)
				&& Objects.equals(login, other.login);
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateTimeDeleted="
				+ dateTimeDeleted + ", login=" + login + "]";
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public LocalDateTime getDateTimeDeleted() {
		return dateTimeDeleted;
	}

	public void setDateTimeDeleted(LocalDateTime dateTimeDeleted) {
		this.dateTimeDeleted = dateTimeDeleted;
	}	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
