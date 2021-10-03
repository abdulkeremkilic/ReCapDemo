package com.example.ReCapProject.business.concretes;

import java.util.List;

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
import com.example.ReCapProject.entities.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao customerDao) {
		this.corporateCustomerDao = customerDao;
	}
	

	@Override
	public Result add(CreateCorporateCustomerRequest entity) {
		
		var result = BusinessRules.run(checkIfEmailExists(entity.getEmail()), checkIfTaxNumberExists(entity.getTaxNumber()));
		
		if(result != null)
			return result;
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setCompanyName(entity.getCompanyName());
		corporateCustomer.setEmail(entity.getEmail().toLowerCase());
		corporateCustomer.setPassword(entity.getPassword());
		corporateCustomer.setTaxNumber(entity.getTaxNumber());
		
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMER_ADDED);
	}
	

	@Override
	public Result update(UpdateCorporateCustomerRequest  entity) {
	
		var result = BusinessRules.run(checkIfEmailExists(entity.getEmail()), checkIfTaxNumberExists(entity.getTaxNumber()));
		
		if(result != null)
			return result;
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(entity.getCorporateCustomerId());
		corporateCustomer.setCompanyName(entity.getCompanyName());
		corporateCustomer.setEmail(entity.getEmail().toLowerCase());
		corporateCustomer.setPassword(entity.getPassword());
		corporateCustomer.setTaxNumber(entity.getTaxNumber());
		
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMER_UPDATED);
	}
	

	@Override
	public Result delete(DeleteCorporateCustomerRequest entity) {
		this.corporateCustomerDao.deleteById(entity.getUserId());
		return new SuccessResult(Messages.CUSTOMER_DELETED);
	}
	

	@Override
	public DataResult<List<CorporateCustomer>> getAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(), Messages.CUSTOMERS_LISTED);
	}
	
	
	private Result checkIfEmailExists(String email) {
		
		if(this.corporateCustomerDao.existsByEmail(email))
			return new ErrorResult(Messages.EMAIL_ALREADY_IN_USE);
		
		return new SuccessResult();
	}
	
	
	private Result checkIfTaxNumberExists(String taxNumber) {
		
		if(this.corporateCustomerDao.existsByEmail(taxNumber))
			return new ErrorResult(Messages.TAX_NUMBER_ALREADY_IN_USE);
		
		return new SuccessResult();
	}

}
