package com.cafemanagementproject.cafemanagementproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cafemanagementproject.cafemanagementproject.constants.CafeConstants;
import com.cafemanagementproject.cafemanagementproject.dto.ProductWrapper;
import com.cafemanagementproject.cafemanagementproject.jwt.JWTAuthenticationFilter;
import com.cafemanagementproject.cafemanagementproject.models.Category;
import com.cafemanagementproject.cafemanagementproject.models.Product;
import com.cafemanagementproject.cafemanagementproject.repository.ProductRepo;
import com.cafemanagementproject.cafemanagementproject.service.ProductService;
import com.cafemanagementproject.cafemanagementproject.utils.CafeUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if (jwtAuthenticationFilter.isAdmin()) {
				if (validateProductMap(requestMap, false)) {
					productRepo.save(getProductFromMap(requestMap, false));
					return CafeUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if (requestMap.containsKey("name")) {
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}
		}
		return false;
	}

	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        
        Product product = new Product();
        if(isAdd) {
        	product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
        	product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
		return product;
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<>(productRepo.getAllProduct(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if(jwtAuthenticationFilter.isAdmin()) {
				if(validateProductMap(requestMap, true)) {
					Optional<Product> optional = productRepo.findById(Integer.parseInt(requestMap.get("id")));
				    if(!optional.isEmpty()) {
				    	Product product = getProductFromMap(requestMap, true);
				    	product.setStatus(optional.get().getStatus());
				    	productRepo.save(product);
				    	return CafeUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
				    }else {
				    	return CafeUtils.getResponseEntity("Product id does not exist.", HttpStatus.OK);
				    }
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		try {
			if(jwtAuthenticationFilter.isAdmin()) {
			 Optional optional = productRepo.findById(id);
			 if(!optional.isEmpty()) {
				 productRepo.deleteById(id);
				 return CafeUtils.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
			 }
			 return CafeUtils.getResponseEntity("Product id isn't exist.", HttpStatus.OK);
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			if(jwtAuthenticationFilter.isAdmin()) {
			Optional optional = productRepo.findById(Integer.parseInt(requestMap.get("id")));
			if(!optional.isEmpty()) {
			  productRepo.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
			  return CafeUtils.getResponseEntity("Product Status Updated Successfully.", HttpStatus.OK);
			}
			return CafeUtils.getResponseEntity("Product id doesn't exist.", HttpStatus.OK);
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
		try {
			return new ResponseEntity<>(productRepo.getProductByCategory(id),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ProductWrapper> getProductById(Integer id) {
		try {
			return new ResponseEntity<>(productRepo.getProductById(id),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
