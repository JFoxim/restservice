package ru.rinat.restservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.rinat.restservice.entity.Address;
import ru.rinat.restservice.entity.Company;
import ru.rinat.restservice.exception.AddressNotFoundException;
import ru.rinat.restservice.repository.AddressRepository;

@Service
public class AddressService {
	
	private AddressRepository addressRepository;
	
	@Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	public List<Address> findAll() {
		return addressRepository.findAll();
	}
	
	public Address findById(UUID id) {
		
		Optional<Address> addressOpt = addressRepository.findById(id);

		if (addressOpt.isEmpty()) {
			throw new AddressNotFoundException("id - " + id);
		}
		return addressOpt.get();
	}
	
	public Address create(Address address) {
		return addressRepository.save(address);
	}
	
	public String update(Address address) {
		checkExistAddress(address);
		
		addressRepository.save(address);
		
		return 	String.format("Адрес с id %s обновлён", address.getId());
	}
	
	public String delete(Address address ) {
		addressRepository.delete(address);
		return String.format("Адрес с id %s удалён", address.getId());
	}
	
	public Optional<Address> findByCityStreetHouseFlat(String city, String street, String house, int flat) {	
		return addressRepository.findByCityAndStreetAndHouseNumberAndFlat(city, street, house, flat);
	}
	
	private void checkExistAddress(Address address) {
		if (!addressRepository.existsById(address.getId())) {
			throw new AddressNotFoundException("id - " + address.getId());
		}
	}
	
	public boolean existsByCityStreetHouseFlat(String city, String street, String house, int flat) {
		Optional<Address> addressOpt = addressRepository.findByCityAndStreetAndHouseNumberAndFlat(city, street, house, flat);
		
		if (addressOpt.isEmpty()) return false;
		
		return true;
	}
}
