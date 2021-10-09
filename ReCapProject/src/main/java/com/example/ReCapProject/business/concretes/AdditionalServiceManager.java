package com.example.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.AdditionalServiceService;
import com.example.ReCapProject.business.constants.messages.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.AdditionalServiceDao;
import com.example.ReCapProject.entities.concretes.AdditionalService;
import com.example.ReCapProject.entities.dtos.AdditionalServiceDetailDto;
import com.example.ReCapProject.entities.requests.additionalService.CreateAdditionalServiceRequest;
import com.example.ReCapProject.entities.requests.additionalService.DeleteAdditionalServiceRequest;
import com.example.ReCapProject.entities.requests.additionalService.UpdateAdditionalServiceRequest;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapper modelMapper) {
		
		this.additionalServiceDao = additionalServiceDao;
		
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest entity) {
	
		var result = BusinessRules.run(checkIfServiceExists(entity.getAdditionalServiceName()));
		
		if(result != null)
			return result;
		
		AdditionalService additionalService = new AdditionalService();
		additionalService.setAdditionalServiceName(entity.getAdditionalServiceName().toLowerCase().trim());
		additionalService.setAdditionalServiceFee(entity.getAdditionalServiceFee());
		
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONAL_SERVICE_ADDED);
	}
	

	@Override
	public Result update(UpdateAdditionalServiceRequest entity) {
		
		var result = BusinessRules.run(checkIfServiceExists(entity.getAdditionalServiceName()));
		
		if(result != null)
			return result;
		
		AdditionalService additionalService = this.additionalServiceDao.getById(entity.getAdditionalServiceId());
		additionalService.setAdditionalServiceName(entity.getAdditionalServiceName().toLowerCase().trim());
		additionalService.setAdditionalServiceFee(entity.getAdditionalServiceFee());
		
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONAL_SERVICE_UPDATED);
	}
	

	@Override
	public Result delete(DeleteAdditionalServiceRequest entity) {
		
		this.additionalServiceDao.deleteById(entity.getAdditionalServiceId());
		return new SuccessResult(Messages.ADDITIONAL_SERVICE_DELETED);
	}
	
	
	@Override
	public DataResult<AdditionalService> getById(int additionalServiceId) {
		return new SuccessDataResult<>(this.additionalServiceDao.getById(additionalServiceId));
	}
	

	@Override
	public DataResult<List<AdditionalService>> getAll() {
		
		return new SuccessDataResult<>(this.additionalServiceDao.findAll(), Messages.ADDITIONAL_SERVICES_LISTED);
	}
	
	

	@Override
	public DataResult<List<AdditionalServiceDetailDto>> getAllDetails() {

		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll();
		
		List<AdditionalServiceDetailDto> additionalServiceDtos = additionalServices.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(additionalServiceDtos, Messages.ADDITIONAL_SERVICES_LISTED);
	}
	

	@Override
	public DataResult<List<AdditionalService>> getByRentalId(int rentalId) {

		return new SuccessDataResult<>(this.additionalServiceDao.getByRental_RentalId(rentalId), Messages.ADDITIONAL_SERVICES_LISTED);
	}


	public Result checkIfServiceExists(String additionalServiceName) {
		
		if(this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName.toLowerCase().trim()))
			return new ErrorResult(Messages.ADDITIONAL_SERVICE_ALREADY_EXISTS);
		
		return new SuccessResult();
	}
	
	
	public AdditionalServiceDetailDto convertToDto(AdditionalService additionalService) {
		
		return modelMapper.map(additionalService, AdditionalServiceDetailDto.class);
	}

}
