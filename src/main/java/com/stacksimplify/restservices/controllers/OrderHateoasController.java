package com.stacksimplify.restservices.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class OrderHateoasController {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public OrderRepository orderRepository;
		
	// getAllOrders method
	@GetMapping("/{id}/orders")
	public ResponseEntity<Collection<Order>> getAllOrders(@PathVariable Long id) 
			throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		
		Collection<Order> orders = orderRepository.findAll();
		List<Order> response = new ArrayList<>();
		
		orders.forEach(order -> {
			order.add(WebMvcLinkBuilder.linkTo(
					this.getClass()).slash(order.getOrderId()).withSelfRel());
					response.add(order);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}
