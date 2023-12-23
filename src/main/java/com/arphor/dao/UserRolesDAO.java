package com.arphor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;

import com.arphor.entity.RoleId;
import com.arphor.entity.UserRoles;

public interface UserRolesDAO extends JpaRepository<UserRoles, Integer>{
	
	UserRoles findByEmail(String email);

	List<UserRoles> findByRole(RoleId userRole);

	void save(Model model);
}