package com.arphor.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.arphor.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer> {
}
