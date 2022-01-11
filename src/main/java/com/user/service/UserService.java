package com.user.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dao.UserRepository;
import com.user.dto.UserDto;
import com.user.exception.UserAlredyExistException;
import com.user.exception.UserNotPresentException;
import com.user.model.User;

@Service

public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper mapper;

	String errorMessage = "user not found";

	public User add(UserDto userDto) throws UserAlredyExistException {
		User user = mapper.map(userDto, User.class);
		if (!(userRepository.existsById(userDto.getUsername()))) {
			userRepository.save(user);
			return user;
		}
		throw new UserAlredyExistException("user alredy exist");
	}

	public List<UserDto> getAllRecords() {
		List<User> userList = (List<User>) userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for (User user : userList) {
			UserDto userDto = mapper.map(user, UserDto.class);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	public UserDto getRecord(String username) throws UserNotPresentException {
		User user = userRepository.findById(username).orElseThrow(() -> new UserNotPresentException(errorMessage));
		return mapper.map(user, UserDto.class);
	}

	public boolean delete(String username) throws UserNotPresentException {
		if (userRepository.existsById(username)) {
			userRepository.deleteById(username);
			return true;
		}
		throw new UserNotPresentException(errorMessage);
	}

	public UserDto updateDetails(String username, UserDto userDto) throws UserNotPresentException {
		if (userRepository.existsById(username)) {
			userDto.setUsername(username);
			User user = mapper.map(userDto, User.class);
			return mapper.map(userRepository.save(user), UserDto.class);

		}
		throw new UserNotPresentException(errorMessage);
	}
}
