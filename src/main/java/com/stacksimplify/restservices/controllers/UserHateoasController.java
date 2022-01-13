package com.stacksimplify.restservices.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	// getAllUsers method
	@GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
		Long userId = null;
		Collection<User> users = userService.getAllUsers();
		List<User> response = new ArrayList<>();
		
		// Self link
		for(User user: users) {
			userId = user.getId();
			user.add(WebMvcLinkBuilder.linkTo(
					this.getClass()).slash(user.getId()).withSelfRel());
					response.add(user);
		
		// Relationship link with getAllOrders
		ResponseEntity<Collection<Order>> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class)
			.getAllOrders(userId);
		Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
		user.add(ordersLink);
		
		};
		// Self link for getAllUsers();
		Link selfLinkgetAllUsers = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
		 
		CollectionModel<User> model = CollectionModel.of(users, selfLinkgetAllUsers);
		
		return model;
		//return new ResponseEntity<>(model.getContent(), HttpStatus.OK);
	}
	
	// getUserById method
	// User OptionalUser to avoid null users
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = null;
			user = userOptional.get();
			user.add(WebMvcLinkBuilder.linkTo(
					this.getClass()).slash(user.getId())
					.withSelfRel());
				
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		} catch(UserNotFoundException unfe) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, unfe.getMessage());
		}
	}
	
}
