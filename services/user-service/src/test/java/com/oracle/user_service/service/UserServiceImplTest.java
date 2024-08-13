package com.oracle.user_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oracle.user_service.dto.UserDto;
import com.oracle.user_service.entity.User;
import com.oracle.user_service.exception.UserServiceException;
import com.oracle.user_service.repository.UserRepository;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void createUserTest_SUCCESS() throws UserServiceException {
    	
    	User user = new User();
    	user.setName("Ramachandran N K");
    	user.setEmail("nnkraam11@gmail.com");
    	user.setPassword("123456");
    	user.setPhoneNumber("123456789");
    	user.setId(1L);
    	
    	UserDto userDto = new UserDto();
    	userDto.setName("Ramachandran N K");
    	userDto.setEmail("nnkraam11@gmail.com");
    	userDto.setPassword("123456");
    	userDto.setPhoneNumber("123456789");
    	userDto.setId(1L);
    	
//    	mock userRepo findByEmail
    	when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
    	
    	//mock userRepo saveAndFlush
    	when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
    	
    	
    	UserDto returnedUser = userService.createUser(userDto);
    	
    	assertEquals(returnedUser.getEmail(), userDto.getEmail());
    	assertEquals(returnedUser.getName(), userDto.getName());
    	
    	verify(userRepository, times(1)).saveAndFlush(any(User.class));
    }
    
    
    @Test
    public void createUserTest_FAIL() {
        UserDto userDto = new UserDto();
        userDto.setName("Ramachandran N K");
        userDto.setEmail("nnkraam11@gmail.com");
        userDto.setPassword("123456");
        userDto.setPhoneNumber("123456789");

        // Mock userRepo findByEmail to return an existing user
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new User()));

        // Verify that the exception is thrown
        assertThrows(UserServiceException.class, () -> userService.createUser(userDto));
        
        // Verify that saveAndFlush is never called
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }
    
    @Test
    public void authUserTest_SUCCESS() throws UserServiceException {
    	
    	
    	User user = new User();
    	user.setName("Ramachandran N K");
    	user.setEmail("nnkraam11@gmail.com");
    	user.setPassword("123456");
    	user.setPhoneNumber("123456789");
    	user.setId(1L);
    	
    	
    	UserDto userDto = new UserDto();
        userDto.setName("Ramachandran N K");
        userDto.setEmail("nnkraam11@gmail.com");
        userDto.setPassword("123456");
        userDto.setPhoneNumber("123456789");
        
        
        //mock userRepo findByEmail
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
    	
        UserDto returnedDto = userService.authUser(userDto);
        
        assertEquals(returnedDto.getName(), userDto.getName());
        assertNull(returnedDto.getPassword());
        
    	
    }
    
    @Test
    public void authUserTest_FAIL() throws UserServiceException {

    	
    	
    	UserDto userDto = new UserDto();
        userDto.setName("Ramachandran N K");
        userDto.setEmail("nnkraam11@gmail.com");
        userDto.setPassword("123456");
        userDto.setPhoneNumber("123456789");
        
        
        //mock userRepo findByEmail
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UserServiceException.class, () -> userService.authUser(userDto));

    }


}
