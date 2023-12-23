package com.arphor.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arphor.entity.StatusProduct;
import com.arphor.service.StatusProductService;

@RestController
public class StatusProductRestController {

	@Autowired
	StatusProductService statusProductService;
	
	@GetMapping("/rest/statusproducts")
    public List<StatusProduct> getAll() {
        return statusProductService.getAll();
    }
}
