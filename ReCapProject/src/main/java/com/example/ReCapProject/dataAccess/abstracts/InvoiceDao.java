package com.example.ReCapProject.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ReCapProject.entities.concretes.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
	
	Invoice getTop1ByOrderByCreationDateDesc();
	Invoice getByRental_RentalId(int rentalId);
	
	List<Invoice> getByRental_User_UserId(int userId);
	List<Invoice> getByCreationDateBetween(LocalDate minDate, LocalDate maxDate);
}
