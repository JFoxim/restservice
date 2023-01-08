package ru.rinat.restservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.rinat.restservice.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
       
	List<User> findByDateTimeDeletedIsNull();  	
	
 	Optional<User> findByLogin(String login);

	User findByFirstNameAndLastName(String firstName, String lastName);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "insert into users_scheme.subscription (creator_user_id, subscriber_user_id) VALUES (:creatorUserId, :subscriberUserId)", nativeQuery = true)
	void addSubscribe(@Param("creatorUserId") UUID creatorUserId, @Param("subscriberUserId") UUID subscriberUserId);
}