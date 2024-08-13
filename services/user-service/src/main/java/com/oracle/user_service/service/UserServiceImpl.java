package com.oracle.user_service.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.user_service.dto.UserDto;
import com.oracle.user_service.entity.User;
import com.oracle.user_service.exception.UserServiceException;
import com.oracle.user_service.repository.UserRepository;

import jakarta.transaction.Transactional;
import java.util.logging.Logger;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	@Override
	public UserDto createUser(UserDto userDto) throws UserServiceException {
		
		
		//check if user already exists
		Optional<User> userOp = userRepo.findByEmail(userDto.getEmail());
		if(!userOp.isEmpty()) {
			LOGGER.info(String.format("Login failed with email : " + userDto.getEmail().toString() ));
			throw new UserServiceException("User with the provided email already exists");
		}
		
		User user = new User();
		user.setAdmin(false);
		user.setUser(true);
		user.setEmail(userDto.getEmail());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setCreatedOn(LocalDateTime.now());
		
		userDto.setPassword(null);
		
		User saved = userRepo.saveAndFlush(user);
		LOGGER.info(String.format("New user added with ID : " + saved.getId().toString()));
		return userDto;
	}



	@Override
	public UserDto authUser(UserDto userDto) throws UserServiceException {
		// TODO Auto-generated method stub
		Optional<User> optional = userRepo.findByEmail(userDto.getEmail());
		
		if(optional.isPresent()) {
			User user = optional.get();
			if(user.getPassword().equals(userDto.getPassword())) {
//				user.setPassword(null);
				LOGGER.info(String.format("User logged in with email : " + user.getEmail()));
				userDto.setPassword(null);
				userDto.setName(user.getName());
				userDto.setId(user.getId());
				return userDto;
			}else {
				//throw failed login exception
				throw new UserServiceException("Invalid Credentials, Please try again");
			}
		}else {
			// throw failed login exception
			throw new UserServiceException("Invalid Credentials, Please try again");
		}
		
		
	}



	@Override
	public User fetchUser(Long userId) {
		// TODO Auto-generated method stub
		
		return userRepo.findById(userId).get();
	}



}
