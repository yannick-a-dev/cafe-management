package com.cafemanagementproject.cafemanagementproject.utils;

import java.util.Date;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {
	
	private CafeUtils() {}

	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}
	
	public static String getUUID() {
		Date date = new Date();
		long time = date.getTime();
		return "BILL-" + time;
	}
	
	public static JSONArray getJsonArrayFromString(String data)throws Exception{
		JSONArray jsonArray = new JSONArray();
		return jsonArray;
	}
	
}
