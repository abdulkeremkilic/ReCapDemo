package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.Maintenance;

@Repository
public interface MaintenanceDao extends JpaRepository<Maintenance, Integer> {

	List<Maintenance> getByCar_CarId(int carId);
}
