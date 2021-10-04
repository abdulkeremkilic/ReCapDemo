package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.BrandService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.BrandDao;
import com.example.ReCapProject.entities.concretes.Brand;
import com.example.ReCapProject.entities.requests.brand.CreateBrandRequest;
import com.example.ReCapProject.entities.requests.brand.DeleteBrandRequest;
import com.example.ReCapProject.entities.requests.brand.UpdateBrandRequest;

@Service
public class BrandManager implements BrandService{

	private BrandDao brandDao;
	
	@Autowired
	public BrandManager(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Override
	public Result add(CreateBrandRequest entity) {
		
		var result = BusinessRules.run(checkIfBrandExists(entity.getBrandName(), entity.getModelName(), entity.getModelYear()));
		
		if(result != null)
			return result;
		
		Brand brand = new Brand();
		brand.setBrandName(entity.getBrandName().toLowerCase().trim());
		brand.setModelName(entity.getModelName().toLowerCase().trim());
		brand.setModelYear(entity.getModelYear());
		
		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRAND_ADDED);
	}
	
	@Override
	public Result update(UpdateBrandRequest entity) {
		
		var result = BusinessRules.run(checkIfBrandExists(entity.getBrandName(), entity.getModelName(), entity.getModelYear()));
		
		if(result != null)
			return result;
		
		Brand brand = this.brandDao.getById(entity.getBrandId());
		brand.setBrandName(entity.getBrandName().toLowerCase().trim());
		brand.setModelName(entity.getModelName().toLowerCase().trim());
		brand.setModelYear(entity.getModelYear());
		
		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRAND_UPDATED);
	}
	
	@Override
	public Result delete(DeleteBrandRequest entity) {
		this.brandDao.deleteById(entity.getBrandId());
		return new SuccessResult(Messages.BRAND_DELETED);
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), Messages.BRANDS_LISTED);
	}

	
	private Result checkIfBrandExists(String brandName, String modelName, int modelYear) {
		if(this.brandDao.existsByBrandNameAndModelNameAndModelYear(brandName.toLowerCase().trim(), modelName.toLowerCase().trim(), modelYear))
			return new ErrorResult(Messages.BRAND_ALREADY_EXISTS);
		
		return new SuccessResult();
	}

}
