package com.example.ReCapProject.business.concretes;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.InvoiceService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.InvoiceDao;
import com.example.ReCapProject.entities.concretes.Invoice;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.dtos.InvoiceDto;
import com.example.ReCapProject.entities.requests.invoice.CreateInvoiceRequest;
import com.example.ReCapProject.entities.requests.invoice.DeleteInvoiceRequest;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao; 
	
	private ModelMapper modelMapper;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapper modelMapper) {
		
		this.invoiceDao = invoiceDao;
		
		this.modelMapper = modelMapper;
	}
	

	@Override
	public Result add(CreateInvoiceRequest entity) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");  
		LocalDateTime dateNow = LocalDateTime.now();
		String year = dtf.format(dateNow);
		
		String lastInvoiceDate = "0000";
		String lastInvoiceNo = "INV0000000000000";

		if (this.invoiceDao.getTop1ByOrderByCreationDateDesc() != null) {
			lastInvoiceDate = dtf.format(this.invoiceDao.getTop1ByOrderByCreationDateDesc().getCreationDate());
			lastInvoiceNo = this.invoiceDao.getTop1ByOrderByCreationDateDesc().getInvoiceNo();
		}

		int lastInvoiceOrder = Integer.parseInt(lastInvoiceNo.substring(7));

		NumberFormat numberFormat = new DecimalFormat("000000000");

		int newInvoiceOrder = lastInvoiceOrder + 1;

		// Resets by year.
		if (!year.equals(lastInvoiceDate)) {
			newInvoiceOrder = 000000001;
		}

		String invoiceNo = "INV" + year + numberFormat.format(newInvoiceOrder);
		
		Rental rental = new Rental();
		rental.setRentalId(entity.getRentalId());

		Invoice invoice = new Invoice();
		invoice.setCreationDate(dateNow);
		invoice.setInvoiceNo(invoiceNo);
		invoice.setRental(rental);
		
		this.invoiceDao.save(invoice);

		return new SuccessResult(Messages.INVOICE_CREATED);
	}
	

	@Override
	public Result delete(DeleteInvoiceRequest entity) {
		this.invoiceDao.deleteById(entity.getInvoiceId());
		return new SuccessResult(Messages.INVOICE_DELETED);
	}
	

	@Override
	public DataResult<List<Invoice>> getAll() {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAll(), Messages.INVOICES_LISTED);
	}
	

	@Override
	public DataResult<List<Invoice>> getByRental_ApplicationUser_UserId(int userId) {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.getByRental_User_UserId(userId), Messages.INVOICES_LISTED);
	}
	

	@Override
	public DataResult<List<Invoice>> getByCreationDateBetween(String firstDate, String lastDate) {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate minDate = LocalDate.parse(firstDate, dateFormat);
		LocalDate maxDate = LocalDate.parse(lastDate, dateFormat);
		
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.getByCreationDateBetween(minDate, maxDate), Messages.INVOICES_LISTED);
	}
	

	@Override
	public DataResult<Invoice> getById(int invoiceId) {
		return new SuccessDataResult<Invoice>(this.invoiceDao.getById(invoiceId), Messages.INVOICE_LISTED);
	}
	
	
	@Override
	public DataResult<InvoiceDto> getInvoiceById(int invoiceId) {
		
		Invoice invoice = this.invoiceDao.getById(invoiceId);
		
		return new SuccessDataResult<InvoiceDto>(modelMapper.map(invoice, InvoiceDto.class), Messages.INVOICE_LISTED);
	}
	

	@Override
	public DataResult<List<InvoiceDto>> getAllInvoiceDetails() {
		
		List<Invoice> invoices = this.invoiceDao.findAll();
		
		List<InvoiceDto> invoiceDtos = invoices.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceDto>>(invoiceDtos, Messages.INVOICES_LISTED);
	}
	
	
	private InvoiceDto convertToDto (Invoice invoice) {
		
		InvoiceDto invoiceDto = modelMapper.map(invoice, InvoiceDto.class);
		return invoiceDto;
	}

	
	@Override
	public Result checkIfInvoiceExists(int rentalId) {
		
		if(this.invoiceDao.getByRental_RentalId(rentalId) != null)
			return new ErrorResult("This rental already closed!");
		
		return new SuccessResult();
	}
	
}
