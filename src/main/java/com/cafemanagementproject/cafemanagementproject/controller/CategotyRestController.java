package com.cafemanagementproject.cafemanagementproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafemanagementproject.cafemanagementproject.models.Category;

@RequestMapping(path = "/category")
public interface CategotyRestController {

	@PostMapping(path = "/add")
	ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path = "/get") //get?filterValue en insérant dans le key=filterValue avec value=true de postman
	public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);
	
	@PostMapping(path = "/update")
	public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);
}
