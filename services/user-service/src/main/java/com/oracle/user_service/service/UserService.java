package com.oracle.user_service.service;

import com.oracle.user_service.dto.UserDto;
import com.oracle.user_service.entity.User;
import com.oracle.user_service.exception.UserServiceException;

public interface UserService {
	
	public UserDto createUser(UserDto user) throws UserServiceException;
	
	
	public UserDto authUser(UserDto user) throws UserServiceException;


	public User fetchUser(Long userId);


}
