package com.arphor.service;

import com.arphor.entity.Discount;

import java.util.List;

public interface DiscountService {

	List<Discount> getAllDiscounts();

    Discount getDiscountById(int discountId);

    Discount createDiscount(Discount discount);

    Discount updateDiscount(Discount discount);

    void deleteDiscount(int discountId);
}
