package com.arphor.service;

import com.arphor.entity.RoleId;

public interface RoleIdService {

	RoleId getRoleById(int roleId);
	
	RoleId getRoleByRoleName(String roleName);
}
