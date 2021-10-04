package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.AdditionalServiceService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.AdditionalServiceDao;
import com.example.ReCapProject.entities.concretes.AdditionalService;
import com.example.ReCapProject.entities.requests.additionalService.CreateAdditionalServiceRequest;
import com.example.ReCapProject.entities.requests.additionalService.DeleteAdditionalServiceRequest;
import com.example.ReCapProject.entities.requests.additionalService.UpdateAdditionalServiceRequest;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao) {
		this.additionalServiceDao = additionalServiceDao;
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
	public DataResult<List<AdditionalService>> getAll() {
		
		return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.findAll(), Messages.ADDITIONAL_SERVICES_LISTED);
	}
	

	@Override
	public DataResult<List<AdditionalService>> getByRentalId(int rentalId) {

		return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.getByRental_RentalId(rentalId), Messages.ADDITIONAL_SERVICES_LISTED);
	}


	public Result checkIfServiceExists(String additionalServiceName) {
		
		if(this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName.toLowerCase().trim()))
			return new ErrorResult(Messages.ADDITIONAL_SERVICE_ALREADY_EXISTS);
		
		return new SuccessResult();
	}

}
