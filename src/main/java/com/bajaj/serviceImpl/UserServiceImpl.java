package com.bajaj.serviceImpl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bajaj.beans.UserBean;
import com.bajaj.dao.UserDao;
import com.bajaj.entity.UserEntity;
import com.bajaj.repository.UserRepository;
import com.bajaj.service.JwtService;
import com.bajaj.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserBean register(UserBean newUser) {
		UserEntity newUserEntity = new UserEntity();
		BeanUtils.copyProperties(newUser, newUserEntity);
		UserEntity userEntity = userDao.register(newUserEntity);
		return null;
	}
	
	public String login(UserBean user) {
		try {
			Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
			if (authentication.isAuthenticated()) {
	            return jwtService.generateToken(user.getName());
	        } else {
	            throw new UsernameNotFoundException("User not found");
	        }
		}catch(Exception e) {
			return e.getLocalizedMessage();
		}
	}


	public UserBean decode(String jwtToken) {
		
		System.out.println(jwtToken);
		UserBean userBean = new UserBean();
		try {
			String name = jwtService.extractUsername(jwtToken.split(" ")[1].toString());
			
			Optional<UserEntity> userEntity = userDao.decode(name);
			
			BeanUtils.copyProperties(userBean, userEntity);
			System.out.print("asd ");
			System.out.println(name);
		}catch(Exception e) {
			System.out.println(jwtToken);
			e.printStackTrace();
		}
		return userBean;

	}
	
	public Optional<UserEntity> findByname(String name) {
        System.out.println("name = " + name);
        Optional<UserEntity> user = userRepository.findByName(name);
        System.out.println("asdasd" + user.toString());
        return user;
    }

}
