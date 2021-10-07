package com.example.ReCapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.Color;

@Repository
public interface ColorDao extends JpaRepository<Color, Integer> {
	
	Color getByColorId(int colorId);
	
	boolean existsByColorName(String colorName);
}
