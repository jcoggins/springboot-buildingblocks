package com.stacksimplify.restservices.controllers;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.entities.Views;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {
	
	@Autowired
	public UserService userService;
	
	// getUserById method
	// User OptionalUser to avoid null users
	@JsonView(Views.External.class)
	@GetMapping("/external/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		
		try {
			return userService.getUserById(id);
		} catch(UserNotFoundException unfe) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, unfe.getMessage());
		}
	}
	
	// getUserById method
	// User OptionalUser to avoid null users
	@GetMapping("/internal/{id}")
	@JsonView(Views.Internal.class)
	public Optional<User> getUserById2(@PathVariable("id") @Min(1) Long id) {
		
		try {
			return userService.getUserById(id);
		} catch(UserNotFoundException unfe) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, unfe.getMessage());
		}
	}
}
