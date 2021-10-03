package com.example.ReCapProject.dataAccess.abstracts;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ReCapProject.entities.concretes.Receipt;

public interface ReceiptDao extends JpaRepository<Receipt, Integer> {

	List<Receipt> getAllByReceiptDateLessThanEqualAndReceiptDateGreaterThanEqual(Date endDate, Date rentDate);
	List<Receipt> getByCardBeholderName(String name);
	
}
