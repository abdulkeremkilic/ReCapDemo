package com.example.ReCapProject.api.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.ReceiptService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.entities.concretes.Receipt;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin
public class ReceiptsController {

	private ReceiptService receiptService;

	@Autowired
	public ReceiptsController(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}
	
	@GetMapping("/getreceiptsbydate")
	public DataResult<List<Receipt>> getReceiptsByDate(Date endDate, Date rentDate) {
		return this.receiptService.getReceiptsByDate(endDate, rentDate);
	}
	
	@GetMapping("/getreceiptsbyname")
	public DataResult<List<Receipt>> getByCardBeholderName(String name) {
		return this.receiptService.getByCardBeholderName(name);
	}
	
	@GetMapping("/getall")
	public DataResult<List<Receipt>> getAll() {
		return this.receiptService.getAll();
	}
}
