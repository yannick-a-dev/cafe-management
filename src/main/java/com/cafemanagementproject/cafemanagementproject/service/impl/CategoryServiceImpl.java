package com.cafemanagementproject.cafemanagementproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cafemanagementproject.cafemanagementproject.constants.CafeConstants;
import com.cafemanagementproject.cafemanagementproject.jwt.JWTAuthenticationFilter;
import com.cafemanagementproject.cafemanagementproject.models.Category;
import com.cafemanagementproject.cafemanagementproject.repository.CategoryRepo;
import com.cafemanagementproject.cafemanagementproject.service.CategoryService;
import com.cafemanagementproject.cafemanagementproject.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			if(jwtAuthenticationFilter.isAdmin()) {
				if(validateCategoryMap(requestMap,false)) {
					categoryRepo.save(getCategoryFromMap(requestMap, false));
					return CafeUtils.getResponseEntity("Categoty Added Successfully", HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}else if(!validateId) {
				return true; 
			}
		}
		return false;
	}

	private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd) {
		Category category = new Category();
		if(isAdd) {
			category.setId(Integer.parseInt(requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		return category;
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
	    try {
			if(!Strings.isEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				log.info("Inside if");
				return new ResponseEntity<List<Category>>(categoryRepo.getAllCategory(),HttpStatus.OK);
			}
			return new ResponseEntity<>(categoryRepo.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			if(jwtAuthenticationFilter.isAdmin()) {
				if(validateCategoryMap(requestMap, true)) {
				   Optional optional = categoryRepo.findById(Integer.parseInt(requestMap.get("id")));
				   if(!optional.isEmpty()) {
					  categoryRepo.save(getCategoryFromMap(requestMap, true)); 
					  return CafeUtils.getResponseEntity("Catefory Update Successfully", HttpStatus.OK);
				   }else {
					   return CafeUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
				   }
				}
				return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
