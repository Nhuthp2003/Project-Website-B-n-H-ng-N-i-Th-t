package com.arphor.service.impi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arphor.dao.ParentDAO;
import com.arphor.entity.Parent;
import com.arphor.service.ParentService;

@Service
public class ParentServiceImpl implements ParentService{
	
	@Autowired
	private ParentDAO parentDAO;

	@Override
    public List<Parent> getAllParents() {
        return parentDAO.findAll();
    }
}
