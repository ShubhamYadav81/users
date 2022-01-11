package com.user.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotPresentException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handlesUserNotFound(UserNotPresentException userNotPresentException,
			HttpServletRequest request) {

		
		Map<String, String> response = new HashMap<>();
		response.put("service", "user");
		response.put("timestamp", new Date().toString());
		response.put("error", userNotPresentException.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.name());
		return response;
	}
	@ExceptionHandler(UserAlredyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handlesUserAlredyExist(UserAlredyExistException userAlredyExistException,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "user");
		response.put("timestamp", new Date().toString());
		response.put("error", userAlredyExistException.getMessage());
		response.put("status", HttpStatus.CONFLICT.name());
		return response;
	}

	

}
