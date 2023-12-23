package com.arphor.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arphor.entity.Order;
import com.arphor.service.OrderService;

@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/all")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/{orderId}")
	public Order getOrderById(@PathVariable Integer orderId) {
		return orderService.getOrderById(orderId);
	}

	@GetMapping("/admin")
	public List<Order> getAll() {
		return orderService.getAllOrders().stream().filter(order -> order.getStatusOrder().getStatusOrderID() == 1)
				.collect(Collectors.toList());
	}

	@PostMapping("/create")
	public Order createOrder(@RequestBody Order order) {
		return orderService.createOrder(order);
	}

	@PutMapping("/update/{orderId}")
	public Order updateOrder(@PathVariable Integer orderId, @RequestBody Order order) {
		return orderService.updateOrder(order);
	}

	@PutMapping("/{orderId}")
	public Order updateOrderStatus(@PathVariable Integer orderId, @RequestBody Order order) {
		order.getStatusOrder().setStatusOrderID(2);
		return orderService.updateOrder(order);
	}

	@DeleteMapping("/delete/{orderId}")
	public void deleteOrder(@PathVariable Integer orderId) {
		orderService.deleteOrder(orderId);
	}
}
