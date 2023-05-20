package com.cafemanagementproject.cafemanagementproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafemanagementproject.cafemanagementproject.dto.ProductWrapper;

@RequestMapping(path = "/product")
public interface ProductRestController {

	@PostMapping(path = "/add")
	public ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);
	
	@GetMapping(path = "/get")
	public ResponseEntity<List<ProductWrapper>> getAllProduct();
	
	@PostMapping(path = "/update")
	public ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);
	
	@PostMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id);
	
	@PostMapping(path = "/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);
	
	@GetMapping(path = "/getByCategory/{id}")
	public ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);
	
	@GetMapping(path = "/getById/{id}")
	public ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer id); 
}
