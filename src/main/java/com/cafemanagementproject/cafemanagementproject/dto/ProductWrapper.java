package com.cafemanagementproject.cafemanagementproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWrapper {

	private Integer Id;
	
	private String name;
	
	private String description;
	
	private Integer price;
	
	private String status;
	
	private Integer categoryId;
	
	private String categoryName;
}
