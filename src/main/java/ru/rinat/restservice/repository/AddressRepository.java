package ru.rinat.restservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.rinat.restservice.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID>{
    
	@Query(value = "SELECT a FROM Address a")
	List<Address> findAllAddresses();
	
	@Query(value = "SELECT a FROM Address a WHERE a.city = :city AND a.street = :street AND a.houseNumber = :houseNumber AND a.flatNumber = :flat")
	Optional<Address> findByCityAndStreetAndHouseNumberAndFlat(@Param("city") String city, 
			@Param("street") String street, @Param("houseNumber") String houseNumber, @Param("flat") int flat);
}
