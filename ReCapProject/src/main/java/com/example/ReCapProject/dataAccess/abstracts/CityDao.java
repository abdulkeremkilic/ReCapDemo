package com.example.ReCapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.City;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {

	boolean existsByCityName(String cityName);
}
