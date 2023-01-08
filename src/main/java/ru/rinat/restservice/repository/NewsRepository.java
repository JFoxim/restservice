package ru.rinat.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rinat.restservice.entity.News;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
       
	List<News> findByUserCreatorId(UUID userCreator);
	
 	Optional<News> findBySubject(String subject);

}