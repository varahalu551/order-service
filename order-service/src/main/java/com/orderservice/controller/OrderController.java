package com.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.entity.Order;
import com.orderservice.model.CustomerResponse;
import com.orderservice.service.CustomerService;
import com.orderservice.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeorder")
	public ResponseEntity<String> postMethodName(@RequestBody Order newOrder) {

	    CustomerResponse customerResponse = customerService.getCustomer(newOrder.getCustomerId());

	    if (customerResponse.getIsError() != null && customerResponse.getIsError()) {

	        String error = customerResponse.getErrorMsg();

	        if (error != null && error.contains("Customer Not Found")) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	        }

	        if (error != null && error.contains("Customer Service Temporarly unavailble, please  try again")) {
	            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
	                    .body("Customer Service Temporarly unavailble, please try again");
	        }
	    }

	    return ResponseEntity.ok("Order Placed for the Customer " + orderService.createOrder(newOrder));
	}

	
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrders(){
		return ResponseEntity.ok(orderService.getAllOrders());
		
	}
}
