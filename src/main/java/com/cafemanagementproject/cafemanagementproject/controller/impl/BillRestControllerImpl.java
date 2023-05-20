//package com.cafemanagementproject.cafemanagementproject.controller.impl;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cafemanagementproject.cafemanagementproject.constants.CafeConstants;
//import com.cafemanagementproject.cafemanagementproject.controller.BillRestController;
//import com.cafemanagementproject.cafemanagementproject.service.BillService;
//import com.cafemanagementproject.cafemanagementproject.utils.CafeUtils;
//
//@RestController
//public class BillRestControllerImpl implements BillRestController {
//
//	@Autowired
//	private BillService billService;
//	
//	@Override
//	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
//		try {
//			return billService.generateReport(requestMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//}
