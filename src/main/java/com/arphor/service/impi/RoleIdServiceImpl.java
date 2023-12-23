package com.arphor.service.impi;

import com.arphor.entity.RoleId;
import com.arphor.dao.RoleIdDAO;
import com.arphor.service.RoleIdService;

import org.springframework.stereotype.Service;

@Service
public class RoleIdServiceImpl implements RoleIdService {

	private final RoleIdDAO roleIdDAO;

    public RoleIdServiceImpl(RoleIdDAO roleIdDAO) {
        this.roleIdDAO = roleIdDAO;
    }

    @Override
    public RoleId getRoleById(int roleId) {
        return roleIdDAO.findById(roleId).orElse(null);
    }

	@Override
	public RoleId getRoleByRoleName(String roleName) {
		return roleIdDAO.getRoleIdByRoleName(roleName);
	}
}