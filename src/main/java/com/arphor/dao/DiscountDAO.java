package com.arphor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arphor.entity.Discount;

public interface DiscountDAO extends JpaRepository<Discount, Integer>{
    
}
