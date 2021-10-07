package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
	
	List<Car> getByBrand_BrandName(String brandName);
	List<Car> getByColor_ColorName(String colorName);
	List<Car> getByCity(String cityName);
}
