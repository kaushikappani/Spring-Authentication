package com.bajaj.service;
import java.util.Optional;

import com.bajaj.beans.UserBean;
import com.bajaj.entity.UserEntity;

public interface UserService {
	public UserBean register(UserBean newUser); 
	public UserBean decode(String jwtToken);
	public String login(UserBean user);
	public Optional<UserEntity> findByname(String name);
}
