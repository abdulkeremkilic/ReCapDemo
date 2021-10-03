package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.dtos.RentalDto;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {
	
	@Query("Select new com.example.ReCapProject.entities.dtos.RentalDto"
			+ " (r.rentalId, r.rentDate, r.returnDate, c.isAvailable, b.modelName, b.brandName, b.modelYear)"
			+ " From Rental r Inner Join r.car c"
			+ " Inner Join c.brand b")
	List<RentalDto> getCarAndRentalDetail();
	
	List<Rental> getByCar_IsAvailableIsFalse();
	List<Rental> getByCar_IsAvailableIsTrue();
}
