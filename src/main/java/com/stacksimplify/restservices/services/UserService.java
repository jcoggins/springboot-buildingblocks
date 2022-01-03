package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	//getAllUsers pass through method
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	//createUser pass through method
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	//getUserById pass through method
	//Optional User is better than null user
	public Optional<User> getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}
	
	//updateUserById pass through method
	public User updateUserById(Long id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}
	
	//deleteUserById pass through method
	public void deleteUserById(Long id) {
		if(userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		}
	}
	
	//getUserByUsername pass through method
	public User getUserByUsername(String username) { 
		return userRepository.findByUsername(username);
	}

}
