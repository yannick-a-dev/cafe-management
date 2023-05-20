package com.cafemanagementproject.cafemanagementproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafemanagementproject.cafemanagementproject.models.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer> {

}
