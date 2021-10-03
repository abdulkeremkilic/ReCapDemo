package com.example.ReCapProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.InvoiceService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.entities.concretes.Invoice;
import com.example.ReCapProject.entities.dtos.InvoiceDto;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin
public class InvoicesController {

	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<Invoice>> getAll() {
		return this.invoiceService.getAll();
	}
	
	@GetMapping("/getbycreationdate")
	public DataResult<List<Invoice>> getByCreationDate(@RequestParam String minDate, @RequestParam String maxDate) {
		return this.invoiceService.getByCreationDateBetween(minDate, maxDate);
	}
	
	@GetMapping("/getbyuserid")
	public DataResult<Invoice> getByUserId(int userId) {
		return this.invoiceService.getById(userId);
	}
	
	@GetMapping("/getinvoicedetails")
	public DataResult<List<InvoiceDto>> getAllInvoiceDetails() {
		return this.invoiceService.getAllInvoiceDetails();
	}
	
}
