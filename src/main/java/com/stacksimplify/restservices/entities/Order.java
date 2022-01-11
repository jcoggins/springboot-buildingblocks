package com.stacksimplify.restservices.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue
	private Long orderId;
	private String orderDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore  		// user data will not be sent
	private User user;  // multiple orders per user

	public Order() {}

	public Long getOrderId() {
		return orderId;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public User getUser() {
		return user;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
