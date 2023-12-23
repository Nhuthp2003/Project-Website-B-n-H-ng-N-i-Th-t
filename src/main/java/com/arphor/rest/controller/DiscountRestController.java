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

import com.arphor.entity.Discount;
import com.arphor.service.DiscountService;

@RestController
@RequestMapping("/rest/discounts")
public class DiscountRestController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @GetMapping("/{discountId}")
    public Discount getDiscountById(@PathVariable Integer discountId) {
        return discountService.getDiscountById(discountId);
    }

    @PostMapping
    public Discount createDiscount(@RequestBody Discount discount) {
        return discountService.createDiscount(discount);
    }

    @PutMapping("/{discountId}")
    public Discount updateDiscount(@PathVariable int discountId, @RequestBody Discount discount) {
        return discountService.updateDiscount(discount);
    }

    @DeleteMapping("/{discountId}")
    public void deleteDiscount(@PathVariable int discountId) {
        discountService.deleteDiscount(discountId);
    }
}
