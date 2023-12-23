package com.arphor.service.impi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arphor.dao.StatusProductDAO;
import com.arphor.entity.StatusProduct;
import com.arphor.service.StatusProductService;

@Service
public class StatusProductServiceImpl implements StatusProductService{
	
	@Autowired
	StatusProductDAO dao;

	@Override
	public List<StatusProduct> getAll() {
		
		return dao.findAll();
	}
}