package com.stacksimplify.restservices.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
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
		User user = optionalUser.get();
		Collection<Order> orders = orderRepository.findAll();
		List<Order> response = new ArrayList<>();
		
		orders.forEach(order -> {
			order.add(WebMvcLinkBuilder.linkTo(
					this.getClass()).slash(user.getId()).slash("orders").slash(order.getOrderId()).withSelfRel());
					response.add(order);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// getOrderById method
	@GetMapping("/{userId}/orders/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long userId, @PathVariable Long orderId) 
			throws UserNotFoundException {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		User user = optionalUser.get();
		for ( Order order: user.getOrders()) {
			if(order.getOrderId().equals(orderId)) {
				
				order.add(WebMvcLinkBuilder.linkTo(
						this.getClass()).slash(user.getId()).slash("orders")
						.slash(order.getOrderId()).withSelfRel());
				
				return new ResponseEntity<>(order, HttpStatus.OK);
			}
		}
		return null;
	}

}
