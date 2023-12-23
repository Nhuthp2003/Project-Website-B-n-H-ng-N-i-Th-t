package com.arphor.service.impi;

import com.arphor.entity.Discount;
import com.arphor.dao.DiscountDAO;
import com.arphor.service.DiscountService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountDAO discountDAO;

    public DiscountServiceImpl(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountDAO.findAll();
    }

    @Override
    public Discount getDiscountById(int discountId) {
        return discountDAO.findById(discountId).orElse(null);
    }

    @Override
    public Discount createDiscount(Discount discount) {
        return discountDAO.save(discount);
    }

    @Override
    public Discount updateDiscount(Discount discount) {
        return discountDAO.save(discount);
    }

    @Override
    public void deleteDiscount(int discountId) {
        discountDAO.deleteById(discountId);
    }
}