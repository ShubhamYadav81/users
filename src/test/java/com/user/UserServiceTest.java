package com.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.user.dao.UserRepository;
import com.user.dto.UserDto;
import com.user.exception.UserAlredyExistException;
import com.user.exception.UserNotPresentException;
import com.user.model.User;
import com.user.service.UserService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

	@InjectMocks
	UserService userServices;

	@MockBean
	UserRepository userRepo;

	@MockBean
	ModelMapper mapper;

	private UserDto userDto;
	private User user;

	@BeforeEach
	void setupUserDtoAndUser() {
		userDto = new UserDto();
		userDto.setUsername("shubham");
		userDto.setName("shubham");
		userDto.setEmail("shubham@email.com");

		user = new User();
		user.setUsername("shubham");
		user.setName("shubham");
		user.setEmail("shubham@email.com");

	}

	@Test
	void shouldReturnUserWhenUserAdd() throws UserAlredyExistException {

		when(mapper.map(any(UserDto.class), any())).thenReturn(new User());
		userServices.add(userDto);
		verify(userRepo, times(1)).save(any(User.class));
	}

	@Test
	void shouldThrowUserAlredyExistExcpetionWhenUserAlredyExists() throws UserAlredyExistException {
		when(mapper.map(any(UserDto.class), any())).thenReturn(new User());
		when(userRepo.existsById("shubham")).thenReturn(true);
		Assertions.assertThrows(UserAlredyExistException.class, () -> {
			userServices.add(userDto);
		});

	}

	@Test
	void shouldReturnListOfUser() {
		List<User> userList = new ArrayList<>();

		userList.add(user);

		User user1 = new User();
		user.setUsername("shubham1");
		user.setName("shubham1");
		user.setEmail("shubham1@email.com");
		userList.add(user1);

		when(userRepo.findAll()).thenReturn(userList);
		when(mapper.map(any(User.class), any())).thenReturn(userDto);
		assertEquals("shubham", userServices.getAllRecords().get(0).getUsername());
	}

	@Test
	void shouldReturnUserWhenUserIsPresent() throws UserNotPresentException {

		Optional<User> optional = Optional.of(user);

		when(userRepo.findById("shubham")).thenReturn(optional);
		when(mapper.map(any(User.class), any())).thenReturn(userDto);

		assertEquals("shubham", userServices.getRecord("shubham").getUsername());

	}

	@Test
	void shouldThrowUserNotFOundExceptionWhenUserIsNotPresent() throws UserNotPresentException {

		when(userRepo.findById("shub")).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotPresentException.class, () -> {
			userServices.getRecord("shubham");
		});
	}

	@Test
	void shouldReturnTrueWhenUserIsDeleted() throws UserNotPresentException {

		
		when(userRepo.existsById("shubham")).thenReturn(true);
		assertTrue(userServices.delete("shubham"));
		verify(userRepo, times(1)).deleteById("shubham");
	}

	@Test
	void shouldThrowUserNotFOundExceptionWhenUserIsNotDeleted() throws UserNotPresentException {

		when(userRepo.findById("shub")).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotPresentException.class, () -> {
			userServices.delete("shubham");
		});
	}

	@Test
	void shouldReturnTrueWhenUserRecordUpdated() throws UserNotPresentException {

		when(userRepo.existsById("shubham")).thenReturn(true);
		when(mapper.map(any(User.class), any())).thenReturn(userDto);
		when(mapper.map(any(UserDto.class),any())).thenReturn(user);
		when(userRepo.save(any(User.class))).thenReturn(user);
		assertEquals("shubham", userServices.updateDetails("shubham", userDto).getUsername());
		verify(userRepo, times(1)).save(any(User.class));
	}

	@Test
	void shouldThrowUserNotFOundExceptionWhenUserIsNotUpdated() throws UserNotPresentException {

		when(userRepo.existsById("shub")).thenReturn(false);
		Assertions.assertThrows(UserNotPresentException.class, () -> {
			userServices.updateDetails("shubham", userDto);
		});
	}
}
