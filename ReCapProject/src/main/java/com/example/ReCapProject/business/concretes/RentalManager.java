package com.example.ReCapProject.business.concretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.InvoiceService;
import com.example.ReCapProject.business.abstracts.RentalService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.services.abstracts.FindexService;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.AdditionalServiceDao;
import com.example.ReCapProject.dataAccess.abstracts.ApplicationUserDao;
import com.example.ReCapProject.dataAccess.abstracts.CarDao;
import com.example.ReCapProject.dataAccess.abstracts.CityDao;
import com.example.ReCapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.example.ReCapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.example.ReCapProject.dataAccess.abstracts.RentalDao;
import com.example.ReCapProject.entities.abstracts.ApplicationUser;
import com.example.ReCapProject.entities.concretes.AdditionalService;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.City;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.dtos.RentalDto;
import com.example.ReCapProject.entities.requests.invoice.CreateInvoiceRequest;
import com.example.ReCapProject.entities.requests.rental.CreateRentalRequest;
import com.example.ReCapProject.entities.requests.rental.DeleteRentalRequest;
import com.example.ReCapProject.entities.requests.rental.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private CarDao carDao;
	private CityDao cityDao;
	private RentalDao rentalDao;
	private ApplicationUserDao applicationUserDao;
	private AdditionalServiceDao additionalServiceDao;
	private CorporateCustomerDao corporateCustomerDao;
	private IndividualCustomerDao individualCustomerDao;
	
	private FindexService findexPointService;
	private InvoiceService invoiceService;
	
	
	@Autowired
	public RentalManager(RentalDao rentalDao, CarDao carDao, CorporateCustomerDao corporateCustomerDao, CityDao cityDao,
			IndividualCustomerDao individualCustomerDao, ApplicationUserDao applicationUserDao, AdditionalServiceDao additionalServiceDao,
			FindexService findexPointService, InvoiceService invoiceService) {
		
		this.carDao = carDao;
		this.cityDao = cityDao;
		this.rentalDao = rentalDao;
		this.applicationUserDao = applicationUserDao;
		this.corporateCustomerDao = corporateCustomerDao;
		this.additionalServiceDao = additionalServiceDao;
		this.individualCustomerDao = individualCustomerDao;
		
		this.findexPointService = findexPointService;
		this.invoiceService = invoiceService;	
	}
	

	@Override
	public Result addForCorporate(CreateRentalRequest entity) {
		
		var result = BusinessRules.run(checkIfCarIsAvailable(), 
				checkCarsPickUpCityIsAvailable(entity.getCarId(), entity.getPickUpCityId()), 
				checkCarReturnCityIsAvailable(entity.getReturnCityId()),
				findexPointService.checkFindexPointsForCorporate(corporateCustomerDao.getById(entity.getUserId()), carDao.getById(entity.getCarId())));

		
		if(result != null)
			return result;

		
		Car car = this.carDao.getById(entity.getCarId());
		car.setAvailable(false);
		
		ApplicationUser user = this.applicationUserDao.getById(entity.getUserId());
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate rentDate = LocalDate.parse(entity.getRentDate(), dateFormat);
		LocalDate returnDate = LocalDate.parse(entity.getReturnDate(), dateFormat);
		    
		Rental rental = new Rental();
		rental.setCar(car);
		rental.setUser(user);
		rental.setRentDate(rentDate);
		rental.setReturnDate(returnDate);
		rental.setPickUpKilometer(car.getCurrentKilometer());
		
		
		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
		for (Integer additionalServiceId : entity.getAdditionalServiceId()) {
			additionalServices.add(this.additionalServiceDao.getById(additionalServiceId));
		}
		rental.setAdditionalServices(additionalServices);
		
		rental.setRentalPrice(calculateTotalPrice(entity.getCarId(), additionalServices, entity.getReturnCityId(), rentDate, returnDate).getData());
		
		this.rentalDao.save(rental);
		this.carDao.save(car);
		
		return new SuccessResult(Messages.RENTAL_ADDED);
	}
	
	
	@Override
	public Result addForIndividual(CreateRentalRequest entity) {
		
		var result = BusinessRules.run(checkIfCarIsAvailable(), 
				findexPointService.checkFindexPointsForIndividual(individualCustomerDao.getById(entity.getUserId()), carDao.getById(entity.getCarId())),
				checkCarsPickUpCityIsAvailable(entity.getCarId(), entity.getPickUpCityId()), checkCarReturnCityIsAvailable(entity.getReturnCityId()));
						
		
		if(result != null)
			return result;
	
		
		Car car = this.carDao.getById(entity.getCarId());
		car.setAvailable(false);
		
		ApplicationUser user = this.applicationUserDao.getById(entity.getUserId());
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate rentDate = LocalDate.parse(entity.getRentDate(), dateFormat);
		LocalDate returnDate = LocalDate.parse(entity.getReturnDate(), dateFormat);
		
		Rental rental = new Rental();
		rental.setCar(car);
		rental.setUser(user);
		rental.setRentDate(rentDate);
		rental.setReturnDate(returnDate);
		rental.setPickUpKilometer(car.getCurrentKilometer());	
		
		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
		for (Integer additionalServiceId : entity.getAdditionalServiceId()) {
			AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);
			additionalServices.add(additionalService);
		}
		rental.setAdditionalServices(additionalServices);
		
		rental.setRentalPrice(calculateTotalPrice(entity.getCarId(), additionalServices, entity.getReturnCityId(), rentDate, returnDate).getData());
		
		this.rentalDao.save(rental);
		this.carDao.save(car);
		
		return new SuccessResult(Messages.RENTAL_ADDED);
	}
	

	@Override
	public Result update(UpdateRentalRequest entity) {
		
		var result = BusinessRules.run(checkCarReturnCityIsAvailable(entity.getReturnCityId()));
		
		if(result != null)
			return result;
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate returnDate = LocalDate.parse(entity.getReturnDate(), dateFormat);
		
		Rental rental = this.rentalDao.getById(entity.getRentalId());
		rental.setReturnKilometer(entity.getReturnKilometer());
		rental.setReturnDate(returnDate);
		
		Car car = rental.getCar();
		car.setAvailable(entity.isReturned());
		car.setCurrentKilometer(entity.getReturnKilometer());
		car.setCity(this.cityDao.getById(entity.getReturnCityId()));
		
		var result2 = BusinessRules.run(checkCarIsReturned(entity));	// When car is returned; the Invoice is being created automatically.
		
		if(result2 != null)
			return result2;
		
		cashBackForUnindicatedReturnDate(entity);
		
		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
		createInvoiceRequest.setRentalId(entity.getRentalId());
		
		this.invoiceService.add(createInvoiceRequest);
		
		this.carDao.save(car);
		this.rentalDao.save(rental);
		
		return new SuccessResult(Messages.RENTAL_UPDATED);
	}
	

	@Override
	public Result delete(DeleteRentalRequest entity) {
		
		Car car = this.rentalDao.getById(entity.getRentalId()).getCar();
		car.setAvailable(true);
		
		this.rentalDao.deleteById(entity.getRentalId());
		return new SuccessResult(Messages.RENTAL_DELETED);
	}


	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALS_LISTED);
	}
	
	
	@Override
	public DataResult<List<RentalDto>> getCarAndRentalDetails() {
		return new SuccessDataResult<List<RentalDto>>(this.rentalDao.getCarAndRentalDetail(), Messages.RENTAL_DETAILS_LISTED);
	}
	
	
	@Override
	public DataResult<List<Rental>> getOpenRentals() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.getByCar_IsAvailableIsFalse(), Messages.RENTALS_LISTED);
	}
	
	
	@Override
	public DataResult<List<Rental>> getClosedRentals() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.getByCar_IsAvailableIsTrue(), Messages.RENTALS_LISTED);
	}
	
	
	private Result checkIfCarIsAvailable() {
		
		if(this.carDao.existsByIsAvailableIsTrue()) 
			return new SuccessResult();
		
		return new ErrorResult(Messages.CAR_IS_NOT_AVAILABLE);
	}
	
	
	private Result checkCarIsReturned(UpdateRentalRequest rental) {
		
		if(rental.isReturned()) 
			return new SuccessResult();
		
		return new ErrorResult(Messages.CAR_IS_NOT_RETURNED);
	}
	
		
	private Result checkCarsPickUpCityIsAvailable(int carId, int pickUpCityId) {
		
		Car car = this.carDao.getById(carId);
		if(car.getCity() != this.cityDao.getById(pickUpCityId))
			return new ErrorResult(Messages.CAR_IS_NOT_IN_THE_CITY);
		
		return new SuccessResult();
	}
	
	
	private Result checkCarReturnCityIsAvailable(int returnCityId) {
		
		for(City city : this.cityDao.findAll()) {
			if(city.getClass() != this.cityDao.getById(returnCityId).getClass()) {
				return new ErrorResult(Messages.CITY_SERVICE_IS_NOT_AVAILABLE);
			}
		}
		return new SuccessResult();
	}
	
	
	private DataResult<Double> calculateTotalPrice(int carId, List<AdditionalService> additionalServices, int returnCityId, LocalDate rentDate, LocalDate returnDate) {
		
		if(returnDate == null) {
			double deposit = (this.carDao.getById(carId).getDailyPrice() * 90);
			return new SuccessDataResult<Double>(deposit);
		}
		double totalPrice =(calculateTotalRentalPrice(carId, additionalServices, rentDate, returnDate).getData() + calculateIfCarReturnedToDifferentCity(carId, returnCityId).getData());
		return new SuccessDataResult<Double>(totalPrice);
	}
	
	
	private Result cashBackForUnindicatedReturnDate(UpdateRentalRequest entity) {
		
		if(rentalDao.getById(entity.getRentalId()).getReturnDate() == null) {	
			if(entity.getReturnDate() != null) {
				System.out.println("Additional fee has been charged for Open-Rent and remaining cash has been sent to User.");
				return new SuccessResult();
			}
		}
		return null;
	}
	
	
	private DataResult<Double> calculateIfCarReturnedToDifferentCity(int carId, int returnCityId) {
		if(this.carDao.getById(carId).getCity() != this.cityDao.getById(returnCityId))
			return new SuccessDataResult<Double>(500.0);
		
		return new SuccessDataResult<Double>(0.0);
	}
	
	
	private DataResult<Double> calculateTotalRentalPrice(int carId, List<AdditionalService> additionalServices, LocalDate rentDate, LocalDate returnDate) {
		
		long days = ChronoUnit.DAYS.between(rentDate, returnDate);
		
		Car car = this.carDao.getById(carId);
		
		return new SuccessDataResult<Double>((car.getDailyPrice() * days) + (calculateTotalFeeForAdditionalServices(additionalServices).getData() * days));			
	}
	
	
	private DataResult<Double> calculateTotalFeeForAdditionalServices(List<AdditionalService> additionalServices) {
		
		double totalFee = 0;
		
		for (AdditionalService additionalService : additionalServices) 
				totalFee += additionalService.getAdditionalServiceFee();			
		
		return new SuccessDataResult<Double>(totalFee);
	}
	
}
