package com.bajaj.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bajaj.beans.UserBean;
import com.bajaj.entity.UserEntity;
import com.bajaj.service.JwtService;
import com.bajaj.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
    private JwtService jwtService;
	
	@GetMapping("/test")
	public Optional<UserEntity> test(@RequestHeader("Authorization") String token) {
		String name = jwtService.extractUsername(token);
		return userService.findByname(name);

	}
	
	@PostMapping("/register")
	public ResponseEntity<UserBean> register(@RequestBody UserBean newUser){
		try {
			userService.register(newUser);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UserBean>(newUser,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserBean user){
		return new ResponseEntity<String>(userService.login(user),HttpStatus.OK);
	}
	


	
	
	
}
