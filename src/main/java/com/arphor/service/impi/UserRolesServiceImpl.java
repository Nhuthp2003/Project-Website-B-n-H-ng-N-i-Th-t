package com.arphor.service.impi;

import com.arphor.entity.Product;
import com.arphor.entity.RoleId;
import com.arphor.entity.UserRoles;
import com.arphor.dao.UserRolesDAO;
import com.arphor.service.UserRolesService;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserRolesServiceImpl implements UserRolesService {

	private final UserRolesDAO userRolesDAO;

    public UserRolesServiceImpl(UserRolesDAO userRolesDAO) {
        this.userRolesDAO = userRolesDAO;
    }
    
    @Override
    public List<UserRoles> getAllUsers() {
        return userRolesDAO.findAll();
    }

    @Override
    public UserRoles getUserByEmail(String email) {
        return userRolesDAO.findByEmail(email);
    }

    @Override
    public void saveUser(UserRoles user) {
    	userRolesDAO.save(user);
    }

	@Override
	public void changePassword(String email, String newPassword) {
        UserRoles userRoles = userRolesDAO.findByEmail(email);
        if (userRoles != null) {
        	userRoles.setPassword(newPassword);
        	userRolesDAO.save(userRoles);
        }
	}

	@Override
	public List<UserRoles> getUsersByRole(RoleId userRole) {
		return userRolesDAO.findByRole(userRole);
	}

	@Override
	public UserRoles getUserById(int userId) {
		return userRolesDAO.findById(userId).orElse(null);
	}

	@Override
	public void deleteUser(int userId, boolean b) {
		UserRoles user = null;
		try {
			user = userRolesDAO.findById(userId).orElseThrow(NotFoundException::new);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (b) {
			user.setDeleted(1);
		} else {
			userRolesDAO.deleteById(userId);
		}

		userRolesDAO.save(user);
	}

	@Override
	public UserRoles updateUser(UserRoles user) {
		// TODO Auto-generated method stub
		return userRolesDAO.save(user);
	}

	@Override
	public void saveUser(Model model) {
		userRolesDAO.save(model);
	}
}