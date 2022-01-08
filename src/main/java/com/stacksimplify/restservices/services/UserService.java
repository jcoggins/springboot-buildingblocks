package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.exceptions.UsernameNotFoundException;
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
	public User createUser(User user) throws UserExistsException {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if(existingUser != null) {
			throw new UserExistsException("User already exists in Repository");
		}
		return userRepository.save(user);
	}
	
	//getUserById pass through method
	//Optional User is better than null user
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("User not found in user Repository");
		}	
		return user;
	}
	
	//updateUserById pass through method
	public User updateUserById(Long id, User user) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException(
					"User not found in user Repository, provide the correct user id");
		}	
		user.setId(id);
		return userRepository.save(user);
	}
	
	//deleteUserById pass through method - non product pattern
	public void deleteUserById(Long id){
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User not found in user Repository, provide the correct user id");
		}	 
		userRepository.deleteById(id);
	}
	
	//getUserByUsername pass through method
	public User getUserByUsername(String username) throws UsernameNotFoundException{ 
		return userRepository.findByUsername(username);
	}

}
