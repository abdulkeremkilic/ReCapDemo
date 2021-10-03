package com.example.ReCapProject.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ReCapProject.business.abstracts.CarImageService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.business.paths.Paths;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CarDao;
import com.example.ReCapProject.dataAccess.abstracts.CarImageDao;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.CarImage;
import com.example.ReCapProject.entities.requests.carImage.CreateCarImageRequest;
import com.example.ReCapProject.entities.requests.carImage.DeleteCarImageRequest;
import com.example.ReCapProject.entities.requests.carImage.UpdateCarImageRequest;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;
	private CarDao carDao;
	
	@Autowired
	public CarImageManager(CarImageDao carImageDao, CarDao carDao) {
		this.carImageDao = carImageDao;
		this.carDao = carDao;
	}
	

	@Override
	public Result add(CreateCarImageRequest entity, MultipartFile file) throws IOException {
		
		var result = BusinessRules.run(checkCarImageLimit(entity.getCarId(), 5), checkCarImageIsEmpty(file), checkImageType(file));
		
		if(result != null)
			return result;
			
		LocalDateTime date = LocalDateTime.now();
		String imageName = UUID.randomUUID().toString();
		
		File myFile = new File(Paths.CAR_IMAGES_PATH + imageName + "." + file.getContentType().substring(file.getContentType().indexOf("/")+1));
		myFile.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(myFile);
		fos.write(file.getBytes());	// Literally copying the file (byte by byte) !
		fos.close();
		
		Car car = this.carDao.getById(entity.getCarId());
		car.setCarId(entity.getCarId());
		
		CarImage carImage = new CarImage();
		carImage.setImagePath(myFile.toString());
		carImage.setDate(date);
		carImage.setCar(car);
				
		this.carImageDao.save(carImage);
		return new SuccessResult(Messages.CAR_IMAGE_ADDED);
	}
	
	
	@Override
	public Result update(UpdateCarImageRequest entity, MultipartFile file) throws IOException {
			
		var result = BusinessRules.run(checkCarImageIsEmpty(file), checkImageType(file));
		
		if(result != null)
			return result;
		
		LocalDateTime date = LocalDateTime.now();
		String imageName = UUID.randomUUID().toString();
		
		File myFile = new File(Paths.CAR_IMAGES_PATH + imageName + "." + file.getContentType().substring(file.getContentType().indexOf("/")+1));
		myFile.createNewFile();
		
		FileOutputStream fileOutpuStream = new FileOutputStream(myFile);
		fileOutpuStream.write(file.getBytes());
		fileOutpuStream.close();
		
		Car car = this.carDao.getById(entity.getCarId());
		car.setCarId(entity.getCarId());
		
		CarImage carImage = this.carImageDao.getById(entity.getCarImageId());
		carImage.setImagePath(myFile.toString());
		carImage.setDate(date);
		carImage.setCar(car);
				
		this.carImageDao.save(carImage);
		return new SuccessResult(Messages.CAR_IMAGE_UPDATED);
	}
	

	@Override
	public Result delete(DeleteCarImageRequest entity) {
		this.carImageDao.deleteById(entity.getCarImageId());
		return new SuccessResult(Messages.CAR_IMAGE_DELETED);
	}
	

	@Override
	public DataResult<List<CarImage>> getAll() {
		return new SuccessDataResult<List<CarImage>>(this.carImageDao.findAll(), Messages.CAR_IMAGES_LISTED);
	}
	

	@Override
	public DataResult<List<CarImage>> getImagesByCarId(int CarId) {
		return new SuccessDataResult<List<CarImage>>(this.carImageDao.getByCar_CarId(CarId), Messages.CAR_IMAGES_LISTED);
	}
	
	
	private Result checkCarImageLimit(int carId, int limit) {
		if(this.carImageDao.countByCar_CarId(carId) >= limit) {
			return new ErrorResult(Messages.CAR_IMAGE_LIMIT_EXCEEDED);
		}
		return new SuccessResult();
	}
	
	
	private Result checkImageType(MultipartFile file) {
			
		if(checkCarImageIsEmpty(file).isSuccess()) {
			
		if(!file.getContentType().substring(file.getContentType().indexOf("/")+1).equals("jpeg")) {
			System.out.println(file.getContentType());
			return new ErrorResult(Messages.CAR_IMAGE_FORMAT_ERROR);
			}
		}
		return new SuccessResult();
	}
	
	
	private Result checkCarImageIsEmpty(MultipartFile file) {
		if(file == null || file.isEmpty() || file.getSize() == 0)
			return new ErrorResult(Messages.CAR_IMAGE_UNUPLOADED);
		
		return new SuccessResult();
	}

}
