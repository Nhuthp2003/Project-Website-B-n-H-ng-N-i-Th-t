package com.arphor.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arphor.entity.Parent;
import com.arphor.service.ParentService;

@RestController
public class ParentRestController {

	@Autowired
	private ParentService parentService;
	
    @GetMapping("/rest/parent")
    public List<Parent> getAll() {
        return parentService.getAllParents();
    } 
}
