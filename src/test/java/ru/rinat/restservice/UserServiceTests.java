package ru.rinat.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.rinat.restservice.entity.User;
import ru.rinat.restservice.exception.UserNotFoundExeption;
import ru.rinat.restservice.service.UserService;

@SpringBootTest
class UserServiceTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	private User preparedData() {
		User user;

		if (!userService.existsByLogin("peter")) {
			User newUser = new User("Пётр", "Петров", "peter");
			user = userService.create(newUser);
		} else {
			user = userService.findByLogin("peter").get();
		}
		return user;
	}

	@Test
	public void userAddTest() {
		User newUser = new User("Иван", "Иванов", "ivan");
		userService.create(newUser);
		User selectedUser = userService.findByLogin("ivan").get();

		assertEquals(newUser, selectedUser);
		
		userService.deleteNotSoft(selectedUser);
	}

	@Test
	public void userUpdateTest() {
		User createdUser = preparedData();
		createdUser.setFirstName("Петя");
		userService.update(createdUser);

		User selectedUser = userService.findById(createdUser.getId());

		assertEquals(createdUser, selectedUser);
		
		userService.deleteNotSoft(selectedUser);
	}

	@Test
	public void userDeleteTest() {
		User createdUser = preparedData();
		userService.delete(createdUser);
		User selectedUser = userService.findById(createdUser.getId());
		
		assertNotNull(selectedUser.getDateTimeDeleted());		
		
		userService.deleteNotSoft(selectedUser);

	    Exception exception = assertThrows(UserNotFoundExeption.class, () -> {
	    	userService.findById(selectedUser.getId());
	    });
	    
	    String expectedMessage = String.format("id - %s", selectedUser.getId());
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
}
