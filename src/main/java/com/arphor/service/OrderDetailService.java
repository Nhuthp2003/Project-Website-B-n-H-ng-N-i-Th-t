package com.arphor.service;

import com.arphor.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

	List<OrderDetail> getAllOrderDetails();

    OrderDetail getOrderDetailById(Integer orderDetailId);

    OrderDetail createOrderDetail(OrderDetail orderDetail);

    OrderDetail updateOrderDetail(OrderDetail orderDetail);

    void deleteOrderDetail(Integer orderDetailId);

	List<OrderDetail> findByOrderID(Integer id);
}
