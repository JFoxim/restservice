package ru.rinat.restservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.rinat.restservice.entity.Address;
import ru.rinat.restservice.entity.News;
import ru.rinat.restservice.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -345819662589854161L;
	private UUID id;
	private String firstName;
	private String lastName;
	private LocalDateTime dateTimeDeleted;
	private String login;
	private String patronymic;
	private String gender;
	@JsonIgnore
	private Address address;
	@JsonIgnore
	private Set<User> subscribedUsers;

	public UserDto() {}

	public UserDto(String firstName, String lastName, LocalDateTime dateTimeDeleted,
				   String login, String patronymic, String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateTimeDeleted = dateTimeDeleted;
		this.login = login;
		this.patronymic = patronymic;
		this.gender = gender;
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
		return "UserDto{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dateTimeDeleted=" + dateTimeDeleted +
				", login='" + login + '\'' +
				", patronymic='" + patronymic + '\'' +
				", gender='" + gender + '\'' +
				'}';
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

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@JsonIgnore
	public Set<User> getSubscribedUsers() {
		return subscribedUsers;
	}

	@JsonIgnore
	public void setSubscribedUsers(Set<User> subscribedUsers) {
		this.subscribedUsers = subscribedUsers;
	}

}
