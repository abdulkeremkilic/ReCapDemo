package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.DamageRecord;

@Repository
public interface DamageRecordDao extends JpaRepository<DamageRecord, Integer> {
	
	List<DamageRecord> getByCar_CarId(int carId);
}
