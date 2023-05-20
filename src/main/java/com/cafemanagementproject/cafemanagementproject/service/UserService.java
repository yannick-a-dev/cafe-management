package com.cafemanagementproject.cafemanagementproject.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.cafemanagementproject.cafemanagementproject.dto.UserWrapper;

public interface UserService {
	
	ResponseEntity<String> signUp(Map<String, String> requestMap);

	ResponseEntity<List<UserWrapper>> getAllUser();

	ResponseEntity<String> login(Map<String, String> requestMap);

	ResponseEntity<String> update(Map<String, String> requMap);

	ResponseEntity<String> checkToken();

	ResponseEntity<String> changePassword(Map<String, String> requestMap);

	ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
