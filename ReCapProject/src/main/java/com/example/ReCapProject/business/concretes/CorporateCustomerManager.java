package com.example.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CorporateCustomerService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.example.ReCapProject.entities.concretes.CorporateCustomer;
import com.example.ReCapProject.entities.dtos.CorporateCustomerDetailDto;
import com.example.ReCapProject.entities.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao customerDao, ModelMapper modelMapper) {
		
		this.corporateCustomerDao = customerDao;
		
		this.modelMapper = modelMapper;
	}
	

	@Override
	public Result add(CreateCorporateCustomerRequest entity) {
		
		var result = BusinessRules.run(checkIfEmailExists(entity.getEmail()), checkIfTaxNumberExists(entity.getTaxNumber()));
		
		if(result != null)
			return result;
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setPassword(entity.getPassword().trim());
		corporateCustomer.setTaxNumber(entity.getTaxNumber().trim());
		corporateCustomer.setCompanyName(entity.getCompanyName().toLowerCase().trim());
		corporateCustomer.setEmail(entity.getEmail().toLowerCase());
		
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMER_ADDED);
	}
	

	@Override
	public Result update(UpdateCorporateCustomerRequest  entity) {
	
		var result = BusinessRules.run(checkIfEmailExists(entity.getEmail()), checkIfTaxNumberExists(entity.getTaxNumber()));
		
		if(result != null)
			return result;
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(entity.getCorporateCustomerId());
		corporateCustomer.setCompanyName(entity.getCompanyName().toLowerCase().trim());
		corporateCustomer.setTaxNumber(entity.getTaxNumber().trim());
		corporateCustomer.setEmail(entity.getEmail().toLowerCase());
		corporateCustomer.setPassword(entity.getPassword().trim());
		
		
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMER_UPDATED);
	}
	

	@Override
	public Result delete(DeleteCorporateCustomerRequest entity) {
		
		this.corporateCustomerDao.deleteById(entity.getUserId());
		return new SuccessResult(Messages.CUSTOMER_DELETED);
	}
	
	
	@Override
	public DataResult<CorporateCustomer> getById(int userId) {
		
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.getById(userId), Messages.CUSTOMER_LISTED);
	}
	

	@Override
	public DataResult<List<CorporateCustomer>> getAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(), Messages.CUSTOMERS_LISTED);
	}
	

	@Override
	public DataResult<CorporateCustomerDetailDto> getDetailsById(int userId) {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(userId);
		
		return new SuccessDataResult<CorporateCustomerDetailDto>(modelMapper.map(corporateCustomer, CorporateCustomerDetailDto.class), Messages.CUSTOMER_LISTED);
	}
	
	

	@Override
	public DataResult<List<CorporateCustomerDetailDto>> getAllCorporateCustomerDetails() {
		
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();
		
		List<CorporateCustomerDetailDto> corporateCustomerDtos = corporateCustomers.stream().map(corporateCustomer -> 
		modelMapper.map(corporateCustomer, CorporateCustomerDetailDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CorporateCustomerDetailDto>>(corporateCustomerDtos, Messages.CUSTOMERS_LISTED); 
	}

		
	private Result checkIfEmailExists(String email) {
		
		if(this.corporateCustomerDao.existsByEmail(email.toLowerCase()))
			return new ErrorResult(Messages.EMAIL_ALREADY_IN_USE);
		
		return new SuccessResult();
	}
	
	
	private Result checkIfTaxNumberExists(String taxNumber) {
		
		if(this.corporateCustomerDao.existsByTaxNumber(taxNumber.trim()))
			return new ErrorResult(Messages.TAX_NUMBER_ALREADY_IN_USE);
		
		return new SuccessResult();
	}

}
