package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {
	
	List<Brand> getByBrandName(String brandName);
	
	boolean existsByBrandNameAndModelNameAndModelYear(String brandName, String modelName, int modelYear);
}
