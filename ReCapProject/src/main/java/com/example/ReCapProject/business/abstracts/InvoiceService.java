package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Invoice;
import com.example.ReCapProject.entities.dtos.InvoiceDetailDto;
import com.example.ReCapProject.entities.requests.invoice.CreateInvoiceRequest;
import com.example.ReCapProject.entities.requests.invoice.DeleteInvoiceRequest;

public interface InvoiceService {

	Result add(CreateInvoiceRequest entity);
	Result delete(DeleteInvoiceRequest entity);
	
	Result checkIfInvoiceExists(int rentalId);
	
	DataResult<Invoice> getById(int invoiceId);
	
	DataResult<List<Invoice>> getAll();
	DataResult<List<Invoice>> getByRental_ApplicationUser_UserId(int userId);
	DataResult<List<Invoice>> getByCreationDateBetween(String minDate, String maxDate);
	
	DataResult<InvoiceDetailDto> getInvoiceById(int invoiceId);
	DataResult<List<InvoiceDetailDto>> getAllInvoiceDetails();
}
