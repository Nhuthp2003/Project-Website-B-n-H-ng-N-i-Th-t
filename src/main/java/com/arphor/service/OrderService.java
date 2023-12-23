package com.arphor.service;

import com.arphor.entity.Order;

import java.util.List;

public interface OrderService {

	List<Order> getAllOrders();

    Order getOrderById(Integer orderId);

    Order createOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(Integer orderId);

	List<Order> getOrdersByCustomer(String email);

	List<Order> findByUsername(String username);

	Order findById(Integer id);

}
