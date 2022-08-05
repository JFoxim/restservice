package ru.rinat.restservice.repository;

import org.springframework.stereotype.Repository;

import ru.rinat.restservice.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
       
	List<User> findByDateTimeDeletedIsNull();  	
	
 	Optional<User> findByLogin(String login);

	User findByFirstNameAndLastName(String firstName, String lastName);
}