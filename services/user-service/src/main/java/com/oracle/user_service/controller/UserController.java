package com.oracle.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.user_service.dto.UserDto;
import com.oracle.user_service.entity.User;
import com.oracle.user_service.exception.UserServiceException;
import com.oracle.user_service.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("create-user")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) throws UserServiceException {
		return new ResponseEntity<UserDto>(userService.createUser(user), HttpStatus.OK);
	}
	
	
	@PostMapping("login")
	public ResponseEntity<UserDto> authUser(@RequestBody UserDto user) throws UserServiceException {
		return new ResponseEntity<UserDto>(userService.authUser(user), HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<User> fetchUser(@RequestParam Long userId){
		return new ResponseEntity<User>(userService.fetchUser(userId), HttpStatus.OK);
	}
	

}
