package com.oracle.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.user_service.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

}
