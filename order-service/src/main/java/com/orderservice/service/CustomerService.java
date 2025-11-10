package com.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderservice.client.CustomerClient;
import com.orderservice.model.Customer;
import com.orderservice.model.CustomerResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CustomerService {

	 @Autowired
	 private CustomerClient customerClient;
	 @CircuitBreaker(name="customerServiceCB", fallbackMethod = "fallbackGetCustomer")
	 public CustomerResponse getCustomer(Long id) {
		 CustomerResponse customerResponse=new CustomerResponse();
		 Customer customer=customerClient.getCustomerById(id);
		 if(customer!=null) {
			 
			 customerResponse.setCustomer(customer);
			 customerResponse.setIsError(false);
		 }
		 return customerResponse;
	 }
	 
	 
	 public CustomerResponse  fallbackGetCustomer(Long id, Throwable t) {
		 CustomerResponse customerResponse=new CustomerResponse();
		 customerResponse.setIsError(true);
		 customerResponse.setErrorMsg("Customer Service Temporarly unavailble, please  try again");
		return customerResponse;
		 
	 }
}
