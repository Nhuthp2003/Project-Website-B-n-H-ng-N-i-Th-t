package com.arphor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arphor.entity.Parent;

public interface ParentDAO extends JpaRepository<Parent, Integer>{
    
}
