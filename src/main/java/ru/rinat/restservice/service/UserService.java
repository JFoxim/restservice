package ru.rinat.restservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.rinat.restservice.controller.UserController;
import ru.rinat.restservice.entity.User;
import ru.rinat.restservice.exception.UserNotFoundExeption;
import ru.rinat.restservice.repository.UserRepository;

@Service
public class UserService {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User create(User user) {
		return userRepository.save(user);
	}

	public String update(User user) {
		checkExistUser(user);

		userRepository.save(user);

		return String.format("Пользователь с id %s обновлён", user.getId());
	}

	private void checkExistUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			throw new UserNotFoundExeption("id - " + user.getId());
		}
	}

	public String delete(User user) {
		checkExistUser(user);

		user.setDateTimeDeleted(LocalDateTime.now());
		userRepository.save(user);

		return String.format("Пользователь с id %s удалён", user.getId());
	}

	public boolean deleteNotSoft(User user) {

		userRepository.delete(user);

		return true;
	}

	public User findById(UUID id) {
		Optional<User> userOpt = userRepository.findById(id);

		if (!userOpt.isPresent()) {
			throw new UserNotFoundExeption("id - " + id);
		}

		return userOpt.get();
	}

	public List<User> findAll() {
		return userRepository.findByDateTimeDeletedIsNull();
	}

	public Optional<User> findByLogin(String login) {	
		return userRepository.findByLogin(login);
	}

	public boolean existsByLogin(String login) {
		Optional<User> userOpt = userRepository.findByLogin(login);
		
		if (!userOpt.isPresent()) return false;
		
		return true;
	}

}
