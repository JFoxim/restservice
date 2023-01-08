package ru.rinat.restservice.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.rinat.restservice.dict.Gender;

@Entity
@Table(name = "users")
public class User {	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	@Column(name = "ID", length = 36)
	private UUID id;
	
	@Column(name = "login", unique = true, nullable = false)
	private String login;
	
	@Column(name = "first_name", nullable = false)
    private String firstName;
	
	@Column(name = "last_name")
    private String lastName;

	@Column(name = "patronymic")
	private String patronymic;

	@Column(name = "gender", nullable = false)
	private String gender;

	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@Column(name = "dt_deleted")
	private LocalDateTime dateTimeDeleted;

	@ManyToMany
	@JoinTable(
			name = "subscription",
			joinColumns = @JoinColumn(name = "creator_user_id"),
			inverseJoinColumns = @JoinColumn(name = "subscriber_user_id"))
	private Set<User> subscribedUsers;

//	@OneToMany(mappedBy = "userCreator")
//	private Set<News> news;
//
	public User() { }

	public User(String login, String firstName, String lastName, String gender) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}

	public User(String login, String firstName, String lastName, String patronymic, String gender, Address address, LocalDateTime dateTimeDeleted) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronymic = patronymic;
		this.gender = gender;
		this.address = address;
		this.dateTimeDeleted = dateTimeDeleted;
	}

	public User(String login, String firstName, String lastName, Gender gender) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender.getName();
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
		User other = (User) obj;
		return Objects.equals(dateTimeDeleted, other.dateTimeDeleted) && Objects.equals(id, other.id)
				&& Objects.equals(login, other.login);
	}


	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

    public LocalDateTime getDateTimeDeleted() {
		return dateTimeDeleted;
	}

	public void setDateTimeDeleted(LocalDateTime dateTimeDeleted) {
		this.dateTimeDeleted = dateTimeDeleted;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<User> getSubscribedUsers() {
		return subscribedUsers;
	}

	public void setSubscribedUsers(Set<User> subscribedUsers) {
		this.subscribedUsers = subscribedUsers;
	}

//	public Set<News> getNews() {
//		return news;
//	}
//
//	public void setNews(Set<News> news) {
//		this.news = news;
//	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", patronymic='" + patronymic + '\'' +
				", gender='" + gender + '\'' +
				", dateTimeDeleted=" + dateTimeDeleted +
				'}';
	}
}
