package com.example.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.IndividualCustomerService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.example.ReCapProject.entities.concretes.IndividualCustomer;
import com.example.ReCapProject.entities.dtos.IndividualCustomerDetailDto;
import com.example.ReCapProject.entities.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.UpdateIndividualCustomerRequest;

@Service
public class IndividualCustomerManager implements IndividualCustomerService{

	private IndividualCustomerDao individualCustomerDao;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapper modelMapper) {
		
		this.individualCustomerDao = individualCustomerDao;
		
		this.modelMapper = modelMapper;
	}
	

	@Override
	public Result add(CreateIndividualCustomerRequest entity) {
		
		var result = BusinessRules.run(checkIfEmailExists(entity.getEmail()), checkIfNationalIdNumberExists(entity.getNationalId()));
		
		if(result != null)
			return result;
		
		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setFirstName(entity.getFirstName().toLowerCase().trim());
		individualCustomer.setLastName(entity.getLastName().toLowerCase().trim());
		individualCustomer.setNationalIdNumber(entity.getNationalId().trim());
		individualCustomer.setEmail(entity.getEmail().toLowerCase());
		individualCustomer.setPassword(entity.getPassword().trim());
		
		
		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.CUSTOMER_ADDED);
	}
	

	@Override
	public Result update(UpdateIndividualCustomerRequest entity) {
		
		var result = BusinessRules.run(checkIfEmailExists(entity.getEmail()));
		
		if(result != null)
			return result;
		
		IndividualCustomer individualCustomer = individualCustomerDao.getById(entity.getIndividualCustomerId());
		individualCustomer.setFirstName(entity.getFirstName().toLowerCase().trim());
		individualCustomer.setLastName(entity.getLastName().toLowerCase().trim());
		individualCustomer.setNationalIdNumber(entity.getNationalId().trim());
		individualCustomer.setEmail(entity.getEmail().toLowerCase());
		individualCustomer.setPassword(entity.getPassword().trim());
		
		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.CUSTOMER_UPDATED);
	}
	

	@Override
	public Result delete(DeleteIndividualCustomerRequest entity) {
		this.individualCustomerDao.deleteById(entity.getUserId());
		return new SuccessResult(Messages.CUSTOMER_DELETED);
	}
	
	
	@Override
	public DataResult<IndividualCustomer> getById(int userId) {
		return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.getById(userId), Messages.CUSTOMER_LISTED);
	}
	

	@Override
	public DataResult<List<IndividualCustomer>> getAll() {
		return new SuccessDataResult<List<IndividualCustomer>>(this.individualCustomerDao.findAll(), Messages.CUSTOMERS_LISTED);
	}
	

	@Override
	public DataResult<IndividualCustomerDetailDto> getDetailsById(int userId) {

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(userId);
		
		return new SuccessDataResult<IndividualCustomerDetailDto>(modelMapper.map(individualCustomer, IndividualCustomerDetailDto.class), Messages.CUSTOMER_LISTED);
	}
	

	@Override
	public DataResult<List<IndividualCustomerDetailDto>> getIndividualCustomersDetail() {
		
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();
		
		List<IndividualCustomerDetailDto> individualCustomerDtos = individualCustomers.stream().map(individualCustomer -> 
		modelMapper.map(individualCustomer, IndividualCustomerDetailDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<IndividualCustomerDetailDto>>(individualCustomerDtos, Messages.CUSTOMERS_LISTED);
		
	}
	
	
	private Result checkIfEmailExists(String email) {
		
		if(this.individualCustomerDao.existsByEmail(email))
			return new ErrorResult(Messages.EMAIL_ALREADY_IN_USE);
		
		return new SuccessResult();		
	}
	
	
	private Result checkIfNationalIdNumberExists(String nationalIdNumber) {
		
		if(this.individualCustomerDao.existsByNationalIdNumber(nationalIdNumber.trim()))
			return new ErrorResult(Messages.NATIONAL_ID_NUMBER_ALREADY_IN_USE);
		
		return new SuccessResult();
	}

}
