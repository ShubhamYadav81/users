package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserDto;
import com.user.exception.UserAlredyExistException;
import com.user.exception.UserNotPresentException;
import com.user.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {
	
	@Autowired
	UserService userServices;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(@RequestBody UserDto userDto) throws UserAlredyExistException {
		userServices.add(userDto);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllRecord() {
		return new ResponseEntity<>(userServices.getAllRecords(), HttpStatus.OK);
	}

	@GetMapping("{username}")
	public ResponseEntity<UserDto> getBook(@PathVariable String username) throws UserNotPresentException {
		return new ResponseEntity<>(userServices.getRecord(username), HttpStatus.OK);
	}

	@DeleteMapping("{username}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable String username) throws UserNotPresentException {
		userServices.delete(username);		
	}

	@PutMapping("{username}")
	public ResponseEntity<UserDto> updateBookDetail(@PathVariable String username, @RequestBody UserDto userDto)
			throws UserNotPresentException {

		return new ResponseEntity<>(userServices.updateDetails(username, userDto), HttpStatus.OK);
	}
}
