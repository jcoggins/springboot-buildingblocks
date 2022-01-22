package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.exceptions.UsernameNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Management RESTful Services",  description = "User Controller for User Management Service")
@RestController
@Validated
@RequestMapping(value = "/users" )
public class UserController {
	
	@Autowired
	public UserService userService;
	
	
	// getAllUsers method
	@Operation(summary = "Retrieve Users", description = "Retrieve list of users")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		
		return userService.getAllUsers();
	}
	
	// createUser method
	@Operation(summary = "Create User", description = "Create a new user")
	@PostMapping
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user, 
			UriComponentsBuilder builder) {
		try {
			userService.createUser(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/users/{id}")
					.buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch(UserExistsException uee) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, uee.getMessage());
		}
	}
	
	// getUserById method
	// User OptionalUser to avoid null users
	@GetMapping("/{id}")
	public Optional<User> getUserById(@Parameter(description = "  Id needed") @PathVariable("id") @Min(1) Long id) {
		
		try {
			return userService.getUserById(id);
		} catch(UserNotFoundException unfe) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, unfe.getMessage());
		}
	}
	
	// updateUserById method
	@PutMapping("/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			
			return userService.updateUserById(id, user);
			
		} catch(UserNotFoundException unfe) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, unfe.getMessage());
		}
	}
	
	// deleteUserById method
	@DeleteMapping("/{id}")
	public void deleteUserById(@PathVariable("id") Long id)  {
		userService.deleteUserById(id);
	}
	
	// getUserByUsername method
	@GetMapping("/byusername/{username}")
	public User getUserByUsername(@PathVariable("username") String username)  throws UsernameNotFoundException { 
		User user = userService.getUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(
					"Username: " + username + " not found in User repository!");
		}
		
		return user;
	}
}
 