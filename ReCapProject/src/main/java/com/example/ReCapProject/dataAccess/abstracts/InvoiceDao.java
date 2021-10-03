package com.example.ReCapProject.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ReCapProject.entities.concretes.Invoice;
import com.example.ReCapProject.entities.dtos.InvoiceDto;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
	
	Invoice getTop1ByOrderByCreationDateDesc();
	
	List<Invoice> getByRental_User_UserId(int userId);
	List<Invoice> getByCreationDateBetween(LocalDate minDate, LocalDate maxDate);
	
	@Query("Select new com.example.ReCapProject.entities.dtos.InvoiceDto"
	  		+ " (i.invoiceNo, i.creationDate, r.rentDate, r.returnDate, r.pickUpKilometer, r.returnKilometer, r.rentalPrice)"
	  		+ " From Rental r Inner Join r.invoice i")
	List<InvoiceDto> getAllInovoiceDetails();
}
