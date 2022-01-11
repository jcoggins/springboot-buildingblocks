package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public OrderRepository orderRepository;
		
	// getAllOrders method
	@GetMapping("/{userId}/orders")
	public List<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		return optionalUser.get().getOrders();
	}
	
	@PostMapping("/{userId}/orders")
	public Order  createOrder(@PathVariable Long userId, @RequestBody Order order) 
			throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		User user = optionalUser.get();
		order.setUser(user);
		
		return orderRepository.save(order);
	}
	// getOrderById method
	@GetMapping("/{userId}/orders/{orderId}")
	public Order getOrderById(@PathVariable Long userId, @PathVariable Long orderId) 
			throws UserNotFoundException {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		User user = optionalUser.get();
		for ( Order order: user.getOrders()) {
			if(order.getOrderId().equals(orderId)) {
				return order;
			}
		}
		return null;
	}
	
	// deleteOrderById method
	@DeleteMapping("/{userId}/orders/{orderId}")
	public void deleteOrderById(@PathVariable Long userId, @PathVariable Long orderId) 
			throws UserNotFoundException {
		Order order = getOrderById(userId, orderId);
		orderRepository.delete(order);	
	}
}
