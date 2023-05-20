package com.cafemanagementproject.cafemanagementproject.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cafemanagementproject.cafemanagementproject.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	List<Category> getAllCategory();
}
