package com.arphor.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arphor.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Integer>{

	List<Order> findByEmail(String email);

	@Query("SELECT o FROM Order o WHERE o.email = :username")
    List<Order> findByUsername(@Param("username") String username);
}
