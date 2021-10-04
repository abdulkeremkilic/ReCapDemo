package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.dtos.CarDetailDto;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
	
	@Query("Select new com.example.ReCapProject.entities.dtos.CarDetailDto"
	  		+ " (c.dailyPrice, b.modelName, b.brandName, b.modelYear, co.colorName, c.isAvailable)"
	  		+ " From Car c Inner Join c.brand b"
			+ " Inner Join c.color co")
	List<CarDetailDto> getCarDetails();
	
	List<Car> getByBrand_BrandName(String brandName);
	List<Car> getByColor_ColorName(String colorName);
	List<Car> getByCity(String cityName);
}
