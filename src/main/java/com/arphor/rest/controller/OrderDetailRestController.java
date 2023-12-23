package com.arphor.rest.controller;

import java.util.List;

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
import com.arphor.entity.OrderDetail;
import com.arphor.service.OrderDetailService;
import com.arphor.service.OrderService;

@RestController
@RequestMapping("/rest/orderdetails")
public class OrderDetailRestController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }

    @GetMapping("/{orderDetailId}")
    public OrderDetail getOrderDetailById(@PathVariable Integer orderDetailId) {
        return orderDetailService.getOrderDetailById(orderDetailId);
    }

    @PostMapping("/create")
	public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail) {
		// Lấy đối tượng Order tương ứng bằng orderDetail.orderId
		Order order = orderService.findById(orderDetail.getOrderId());

		// Liên kết đối tượng OrderDetail với đối tượng Order
		orderDetail.setOrder(order);

		// Lưu đối tượng OrderDetail vào cơ sở dữ liệu
		return orderDetailService.createOrderDetail(orderDetail);
	}


    @PutMapping("/update/{orderDetailId}")
    public OrderDetail updateOrderDetail(@PathVariable Integer orderDetailId, @RequestBody OrderDetail orderDetail) {
        return orderDetailService.updateOrderDetail(orderDetail);
    }

    @DeleteMapping("/{orderDetailId}")
    public void deleteOrderDetail(@PathVariable Integer orderDetailId) {
        orderDetailService.deleteOrderDetail(orderDetailId);
    }
}
