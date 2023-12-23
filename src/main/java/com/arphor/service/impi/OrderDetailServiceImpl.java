package com.arphor.service.impi;

import com.arphor.entity.OrderDetail;
import com.arphor.dao.OrderDetailDAO;
import com.arphor.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDAO orderDetailDAO;

    public OrderDetailServiceImpl(OrderDetailDAO orderDetailDAO) {
        this.orderDetailDAO = orderDetailDAO;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailDAO.findAll();
    }

    @Override
    public OrderDetail getOrderDetailById(Integer orderDetailId) {
        return orderDetailDAO.findById(orderDetailId).orElse(null);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailDAO.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailDAO.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Integer orderDetailId) {
        orderDetailDAO.deleteById(orderDetailId);
    }

	@Override
	public List<OrderDetail> findByOrderID(Integer id) {
		return orderDetailDAO.findByUsername(id);
	}
}