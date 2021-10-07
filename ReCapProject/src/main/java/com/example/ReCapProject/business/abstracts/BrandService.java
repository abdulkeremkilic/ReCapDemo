package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Brand;
import com.example.ReCapProject.entities.dtos.BrandDetailDto;
import com.example.ReCapProject.entities.requests.brand.CreateBrandRequest;
import com.example.ReCapProject.entities.requests.brand.DeleteBrandRequest;
import com.example.ReCapProject.entities.requests.brand.UpdateBrandRequest;

public interface BrandService {
	
	Result add(CreateBrandRequest entity);
	Result update(UpdateBrandRequest entity);
	Result delete(DeleteBrandRequest entity);
	
	DataResult<List<BrandDetailDto>> getAllBrandDetails();
	
	DataResult<Brand> getById(int brandId);
	
	DataResult<List<Brand>> getAll();
	
}
