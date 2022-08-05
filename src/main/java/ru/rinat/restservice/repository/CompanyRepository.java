package ru.rinat.restservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.rinat.restservice.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    
	@Query(value = "SELECT c FROM Company c")
	List<Company> findAllCompanies();
	
	Optional<Company> findByName(String name);
}
