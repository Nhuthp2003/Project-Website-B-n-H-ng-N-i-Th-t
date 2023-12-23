package com.arphor.service.impi;

import com.arphor.entity.Order;
import com.arphor.entity.StatusOrder;
import com.arphor.dao.OrderDAO;
import com.arphor.service.OrderService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDAO orderDAO;

	public OrderServiceImpl(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderDAO.findAll();
	}

	@Override
	public Order getOrderById(Integer orderId) {
		return orderDAO.findById(orderId).orElse(null);
	}

	@Override
	public Order createOrder(Order order) {
		order.setStatusOrder(new StatusOrder(1, null, null));
		return orderDAO.save(order);
	}

	@Override
	public Order updateOrder(Order order) {
		return orderDAO.save(order);
	}

	@Override
	public void deleteOrder(Integer orderId) {
		orderDAO.deleteById(orderId);
	}

	@Override
	public List<Order> getOrdersByCustomer(String email) {
		return orderDAO.findByEmail(email);
	}

	@Override
	public List<Order> findByUsername(String username) {
		return orderDAO.findByUsername(username);
	}

	@Override
	public Order findById(Integer id) {
		return orderDAO.findById(id).get();
	}
}
