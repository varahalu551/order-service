package com.orderservice.service;

import com.orderservice.entity.Order;
import java.util.List;

public interface OrderService {
List<Order> getAllOrders();
Order createOrder(Order newOrder);
	
}
