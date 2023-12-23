package com.arphor.service.impi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arphor.dao.StatusOrderDAO;
import com.arphor.entity.StatusOrder;
import com.arphor.service.StatusOrderService;

@Service
public class StatusOrderServiceImpl implements StatusOrderService{
	@Autowired
	StatusOrderDAO dao;

	public List<StatusOrder> getAll() {

		return dao.findAll();
	}
}

