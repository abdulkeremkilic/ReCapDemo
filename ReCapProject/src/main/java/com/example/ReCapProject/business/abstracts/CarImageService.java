package com.example.ReCapProject.business.abstracts;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.CarImage;
import com.example.ReCapProject.entities.requests.carImage.CreateCarImageRequest;
import com.example.ReCapProject.entities.requests.carImage.DeleteCarImageRequest;
import com.example.ReCapProject.entities.requests.carImage.UpdateCarImageRequest;

public interface CarImageService {

	Result add(CreateCarImageRequest entity, MultipartFile file) throws IOException;
	Result update(UpdateCarImageRequest entity, MultipartFile file) throws IOException;
	Result delete(DeleteCarImageRequest entity);
	
	DataResult<List<CarImage>> getImagesByCarId(int CarId);
	DataResult<List<CarImage>> getAll();
	
}
