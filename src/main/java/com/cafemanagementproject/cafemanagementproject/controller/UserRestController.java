package com.cafemanagementproject.cafemanagementproject.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cafemanagementproject.cafemanagementproject.dto.UserWrapper;

@RequestMapping(path = "/user")
public interface UserRestController {

	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);
	
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path = "/get")
	public ResponseEntity<List<UserWrapper>> getAllUser();
	
	@PostMapping(path = "/update")
	public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requMap);
	
	@GetMapping(path = "/checkToken")
	public ResponseEntity<String> checkToken();
	
	@PostMapping(path = "/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);
	
	@PostMapping(path = "/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);
}
