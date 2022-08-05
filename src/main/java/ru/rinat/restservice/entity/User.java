package ru.rinat.restservice.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
	
	@Column(name = "last_name", nullable = false)
    private String lastName;
	
	@Column(name = "dt_deleted")
	private LocalDateTime dateTimeDeleted;
	
	public User() { }

    public User(String firstName, String lastName, String login) {
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
		User other = (User) obj;
		return Objects.equals(dateTimeDeleted, other.dateTimeDeleted) && Objects.equals(id, other.id)
				&& Objects.equals(login, other.login);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateTimeDeleted=" + dateTimeDeleted + "]";
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
}
