package com.arphor.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arphor.entity.StatusOrder;
import com.arphor.service.StatusOrderService;

@RestController
public class StatusOrderRestController {

	@Autowired
	StatusOrderService statusOrderService;
	
	@GetMapping("/rest/statusorders")
    public List<StatusOrder> getAll() {
        return statusOrderService.getAll();
    }
}
