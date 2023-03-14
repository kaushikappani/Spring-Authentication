package com.bajaj.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.bajaj.entity.UserEntity;
import com.bajaj.repository.UserRepository;

@Repository
public class UserDao {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	public UserEntity register(UserEntity newUser) {
		try {
			String encodedPassword = passwordEncoder.encode(newUser.getPassword());
			newUser.setPassword(encodedPassword);
			userRepository.save(newUser);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return newUser;
	};
	public Optional<UserEntity> decode (String name) {
		return userRepository.findByName(name);
	};
}


