package com.arphor.VNPay;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.arphor.entity.Order;
import com.arphor.service.OrderService;

import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VNPayController {

	@Autowired
	private VNPayService vnPayService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/VNPay")
	public String index() {
		return "VNPay/index";
	}

	@PostMapping("/order/submitOrder")
	public String submidOrder(@RequestParam("totalPrice") int totalPrice,

			HttpServletRequest request) {
		
		
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(totalPrice, baseUrl);

		return "redirect:" + vnpayUrl;
	}

	@GetMapping("/cancelVNpay")
	public String GetMapping(HttpServletRequest request, Model model, @ModelAttribute("order") Order order) {
		int paymentStatus = vnPayService.orderReturn(request);

		if (paymentStatus != 1) {
			return "cancel";
		}

		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");

		order.setOrderDate(new Date());
		orderService.createOrder(order);

		return "redirect: /order/invoice";
	}
}
