package com.arphor.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arphor.entity.RoleId;

public interface RoleIdDAO extends JpaRepository<RoleId, Integer>{
	@Query("SELECT r FROM RoleId r WHERE r.roleName = :roleName")
	RoleId getRoleIdByRoleName(@Param("roleName") String roleName);

}