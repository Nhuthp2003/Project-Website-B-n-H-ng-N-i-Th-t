package com.arphor.VNPay;

import javax.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createOrder(int totalPrice, String urlReturn);
    int orderReturn(HttpServletRequest request);
}
