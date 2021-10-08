package com.example.ReCapProject.business.concretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.AdditionalServiceService;
import com.example.ReCapProject.business.abstracts.ApplicationUserService;
import com.example.ReCapProject.business.abstracts.CarService;
import com.example.ReCapProject.business.abstracts.CityService;
import com.example.ReCapProject.business.abstracts.CorporateCustomerService;
import com.example.ReCapProject.business.abstracts.IndividualCustomerService;
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
import com.example.ReCapProject.dataAccess.abstracts.RentalDao;
import com.example.ReCapProject.entities.abstracts.ApplicationUser;
import com.example.ReCapProject.entities.concretes.AdditionalService;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.City;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.dtos.AdditionalServiceDetailDto;
import com.example.ReCapProject.entities.dtos.RentalDetailDto;
import com.example.ReCapProject.entities.requests.invoice.CreateInvoiceRequest;
import com.example.ReCapProject.entities.requests.rental.CreateRentalRequest;
import com.example.ReCapProject.entities.requests.rental.DeleteRentalRequest;
import com.example.ReCapProject.entities.requests.rental.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	
	private CarService carService;
	private CityService cityService;
	private InvoiceService invoiceService;
	private FindexService findexPointService;
	private ApplicationUserService applicationUserService;
	private AdditionalServiceService additionalServiceService;
	private CorporateCustomerService corporateCustomerService;
	private IndividualCustomerService individualCustomerService;	
	
	private ModelMapper modelMapper;
	
	@Autowired
	public RentalManager(RentalDao rentalDao, CarService carService, CorporateCustomerService corporateCustomerService, CityService cityService,
			IndividualCustomerService individualCustomerService, ApplicationUserService applicationUserService, AdditionalServiceService additionalServiceService,
			FindexService findexPointService, InvoiceService invoiceService, ModelMapper modelMapper) {
		
		this.rentalDao = rentalDao;
		
		this.carService = carService;
		this.cityService = cityService;
		this.invoiceService = invoiceService;	
		this.findexPointService = findexPointService;
		this.applicationUserService = applicationUserService;
		this.additionalServiceService = additionalServiceService;
		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
		
		this.modelMapper = modelMapper;
	}
	

	@Override
	public Result addForCorporate(CreateRentalRequest entity) {
		
		var result = BusinessRules.run(checkIfCarIsAvailable(entity.getCarId()), 
				checkCarsPickUpCityIsAvailable(entity.getCarId(), entity.getPickUpCityId()), 
				checkCarReturnCityIsAvailable(entity.getReturnCityId()),
				findexPointService.checkFindexPointsForCorporate(corporateCustomerService.getById(entity.getUserId()).getData(), carService.getById(entity.getCarId()).getData()),
				checkRentalDates(entity.getRentDate(), entity.getReturnDate()));

		
		if(result != null)
			return result;

		
		Car car = this.carService.getById(entity.getCarId()).getData();
		
		ApplicationUser user = this.applicationUserService.getById(entity.getUserId()).getData();
		
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
		
		if(entity.getAdditionalServiceId() != null) {
		for (Integer additionalServiceId : entity.getAdditionalServiceId()) {
			additionalServices.add(this.additionalServiceService.getById(additionalServiceId).getData());
			}
		}
		rental.setAdditionalServices(additionalServices);
		rental.setRentalPrice(calculateTotalPrice(entity.getCarId(), additionalServices, entity.getReturnCityId(), rentDate, returnDate).getData());
		
		this.rentalDao.save(rental);
		
		return new SuccessResult(Messages.RENTAL_ADDED);
	}
	
	
	@Override
	public Result addForIndividual(CreateRentalRequest entity) {
		
		var result = BusinessRules.run(checkIfCarIsAvailable(entity.getCarId()),
				findexPointService.checkFindexPointsForIndividual(individualCustomerService.getById(entity.getUserId()).getData(), carService.getById(entity.getCarId()).getData()),
				checkCarsPickUpCityIsAvailable(entity.getCarId(), entity.getPickUpCityId()), checkCarReturnCityIsAvailable(entity.getReturnCityId()),
				checkRentalDates(entity.getRentDate(), entity.getReturnDate()));
						
		
		if(result != null)
			return result;
	
		Car car = this.carService.getById(entity.getCarId()).getData();
		
		ApplicationUser user = this.applicationUserService.getById(entity.getUserId()).getData();
		
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
		
		if(entity.getAdditionalServiceId() != null) {
		for (Integer additionalServiceId : entity.getAdditionalServiceId()) {
			AdditionalService additionalService = this.additionalServiceService.getById(additionalServiceId).getData();
			additionalServices.add(additionalService);
			}
		}
		rental.setAdditionalServices(additionalServices);
		rental.setRentalPrice(calculateTotalPrice(entity.getCarId(), additionalServices, entity.getReturnCityId(), rentDate, returnDate).getData());
		
		this.rentalDao.save(rental);
		
		return new SuccessResult(Messages.RENTAL_ADDED);
	}
	

	@Override
	public Result update(UpdateRentalRequest entity) {
		
		var result = BusinessRules.run(checkCarReturnCityIsAvailable(entity.getReturnCityId()), 
				checkReturnDate(this.rentalDao.getById(entity.getRentalId()).getRentDate(), entity.getReturnDate()),
				checkIfReturnIsDelayed(entity.getRentalId(), entity.getReturnDate()),
				checkIfReturnIsEarly(entity.getRentalId(), entity.getReturnDate()),
				invoiceService.checkIfInvoiceExists(entity.getRentalId()), 
				checkIfRentalIsPayed(entity.getRentalId()));
		
		if(result != null)
			return result;
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate returnDate = LocalDate.parse(entity.getReturnDate(), dateFormat);
		
		Rental rental = this.rentalDao.getById(entity.getRentalId());
		rental.setReturnKilometer(entity.getReturnKilometer());
		rental.setReturnDate(returnDate);
		rental.setReturned(entity.isReturned());
		
		List<AdditionalService> additionalServices = rental.getAdditionalServices();
		
		if(entity.getAdditionalServiceId() != null) {
		for (Integer additionalServiceId : entity.getAdditionalServiceId()) {
			additionalServices.add(this.additionalServiceService.getById(additionalServiceId).getData());
			}
		}
		rental.setRentalPrice(calculateTotalPrice(rental.getCar().getCarId(), 
				additionalServices, entity.getReturnCityId(), 
				this.rentalDao.getById(entity.getRentalId()).getRentDate(), 
				returnDate).getData());
		
		Car car = rental.getCar();
		car.setAvailable(entity.isReturned());
		car.setCurrentKilometer(entity.getReturnKilometer());
		car.setCity(this.cityService.getById(entity.getReturnCityId()).getData());
		
		this.rentalDao.saveAndFlush(rental);
		
		var result2 = BusinessRules.run(checkIfCarIsReturned(entity));	// When car is returned and the rental is payed; the Invoice is being created automatically.
		
		if(result2 != null)
			return result2;
		
		cashBackForUnindicatedReturnDate(entity);
		
		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
		createInvoiceRequest.setRentalId(entity.getRentalId());
		
		this.invoiceService.add(createInvoiceRequest);
		
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
	public Result deleteById(int rentalId) {
		
		this.rentalDao.deleteById(rentalId);
		return new SuccessResult();
	}

	
	@Override
	public DataResult<Rental> getById(int rentalId) {
		
		return new SuccessDataResult<Rental>(this.rentalDao.getById(rentalId));
	}
	

	@Override
	public DataResult<List<Rental>> getAll() {
		
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALS_LISTED);
	}
	
	//---------------- Bug'
	@Override
	public DataResult<RentalDetailDto> getRentalDetailById(int rentalId) {
		
		Rental rental = this.rentalDao.getById(rentalId);
		
		RentalDetailDto rentalDto = modelMapper.map(rental, RentalDetailDto.class);
		
		List<AdditionalServiceDetailDto> additionalServiceDetailDtos = new ArrayList<AdditionalServiceDetailDto>();
		
		for(AdditionalService additionalService : rental.getAdditionalServices()) {
			
			AdditionalServiceDetailDto additionalServiceDetailDto = modelMapper.map(additionalService, AdditionalServiceDetailDto.class);
			additionalServiceDetailDtos.add(additionalServiceDetailDto);
		}
		
		rentalDto.setAdditionalServices(additionalServiceDetailDtos);
		
		return new SuccessDataResult<RentalDetailDto>(rentalDto);
	}
	
	
	@Override
	public DataResult<List<RentalDetailDto>> getRentalDetails() {
		
		List<Rental> rentals = this.rentalDao.findAll();
		
		List<RentalDetailDto> rentalDtos = rentals.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentalDetailDto>>(rentalDtos, Messages.RENTALS_LISTED);
	}
	
	
	@Override
	public DataResult<List<RentalDetailDto>> getOpenRentals() {
	
		List<Rental> rentals = this.rentalDao.getByIsPayedIsTrueAndIsReturnedIsFalse();
		
		List<RentalDetailDto> rentalDtos = rentals.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentalDetailDto>>(rentalDtos, Messages.RENTALS_LISTED);
	}
	
	
	@Override
	public DataResult<List<RentalDetailDto>> getClosedRentals() {

		List<Rental> rentals = this.rentalDao.getByIsPayedIsTrueAndIsReturnedIsTrue();
		
		List<RentalDetailDto> rentalDtos = rentals.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentalDetailDto>>(rentalDtos, Messages.RENTALS_LISTED);
	}
	
	
	private Result checkIfCarIsAvailable(int carId) {
		
		if(this.carService.getById(carId).getData().isAvailable()) 
			return new SuccessResult();
		
		return new ErrorResult(Messages.CAR_IS_NOT_AVAILABLE);
	}
	
	
	private Result checkIfCarIsReturned(UpdateRentalRequest rental) {
		
		if(rental.isReturned()) 
			return new SuccessResult();
		
		return new ErrorResult(Messages.CAR_IS_NOT_RETURNED);
	}
	
	
	private Result checkIfRentalIsPayed(int rentalId) {
		
		if(this.rentalDao.getById(rentalId).isPayed()) 
			return new SuccessResult();
		
		return new ErrorResult(Messages.RENTAL_IS_NOT_PAYED);
	}
	
		
	private Result checkCarsPickUpCityIsAvailable(int carId, int pickUpCityId) {
		
		Car car = this.carService.getById(carId).getData();
		if(car.getCity() != this.cityService.getById(pickUpCityId).getData())
			return new ErrorResult(Messages.CAR_IS_NOT_IN_THE_CITY);
		
		return new SuccessResult();
	}
	
	
	private Result checkCarReturnCityIsAvailable(int returnCityId) {
		
		for(City city : this.cityService.getAll().getData()) {
			if(city.getClass() != this.cityService.getById(returnCityId).getData().getClass()) {
				return new ErrorResult(Messages.CITY_SERVICE_IS_NOT_AVAILABLE);
			}
		}
		return new SuccessResult();
	}
	
	
	private DataResult<Double> calculateTotalPrice(int carId, List<AdditionalService> additionalServices, int returnCityId, LocalDate rentDate, LocalDate returnDate) {
		
		if(returnDate == null) {
			double deposit = (this.carService.getById(carId).getData().getDailyPrice() * 90);
			return new SuccessDataResult<Double>(deposit);
		}
		double totalPrice =(calculateRentalPrice(carId, additionalServices, rentDate, returnDate).getData() + calculateIfCarReturnedToDifferentCity(carId, returnCityId).getData());
		return new SuccessDataResult<Double>(totalPrice);
	}
	
	
	private Result cashBackForUnindicatedReturnDate(UpdateRentalRequest entity) {
		
		if(rentalDao.getById(entity.getRentalId()).getReturnDate() == null) {	
			if(entity.getReturnDate() != null) {
				System.out.println(Messages.RENTAL_REMAINING_CASH_RETURNED);
				return new SuccessResult();
			}
		}
		return null;
	}
	
	
	private DataResult<Double> calculateIfCarReturnedToDifferentCity(int carId, int returnCityId) {
		if(this.carService.getById(carId).getData().getCity() != this.cityService.getById(returnCityId).getData())
			return new SuccessDataResult<Double>(500.0);
		
		return new SuccessDataResult<Double>(0.0);
	}
	
	
	private DataResult<Double> calculateRentalPrice(int carId, List<AdditionalService> additionalServices, LocalDate rentDate, LocalDate returnDate) {
		
		long days = ChronoUnit.DAYS.between(rentDate, returnDate);
		
		Car car = this.carService.getById(carId).getData();
		
		return new SuccessDataResult<Double>((car.getDailyPrice() * days) + (calculateTotalFeeForAdditionalServices(additionalServices).getData() * days));			
	}
	
	
	private DataResult<Double> calculateTotalFeeForAdditionalServices(List<AdditionalService> additionalServices) {
		
		double totalFee = 0;
		
		for (AdditionalService additionalService : additionalServices) 
				totalFee += additionalService.getAdditionalServiceFee();			
		
		return new SuccessDataResult<Double>(totalFee);
	}
	
	
	private Result checkRentalDates(String rentDate, String returnDate) {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate rentDate1 = LocalDate.parse(rentDate, dateFormat);
		LocalDate returnDate1 = LocalDate.parse(returnDate, dateFormat);
		LocalDate today = LocalDate.parse(LocalDate.now().toString());
		
		if(rentDate1.isBefore(today) || returnDate1.isBefore(rentDate1))
			return new ErrorResult(Messages.INVALID_DATES);
		
		return new SuccessResult();
	}
	
	
	private Result checkReturnDate(LocalDate rentDate, String returnDate) {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate returnDate1 = LocalDate.parse(returnDate, dateFormat);
		
		if(returnDate1.isBefore(rentDate))
			return new ErrorResult(Messages.INVALID_RETURN_DATE);
		
		return new SuccessResult();
	}
	
	
	private Result checkIfReturnIsDelayed(int rentalId, String returnDate) {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate returnDate1 = LocalDate.parse(returnDate, dateFormat);
		
		if(this.rentalDao.getById(rentalId).getReturnDate() != null && returnDate1.isAfter(this.rentalDao.getById(rentalId).getReturnDate())) 
			System.out.println(Messages.CAR_HAS_BEEN_DELAYED);
		
		return new SuccessResult();
	}
	
	
	private Result checkIfReturnIsEarly(int rentalId, String returnDate) {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate returnDate1 = LocalDate.parse(returnDate, dateFormat);
		
		if(this.rentalDao.getById(rentalId).getReturnDate() != null && returnDate1.isBefore(this.rentalDao.getById(rentalId).getReturnDate())) 
			System.out.println(Messages.CAR_HAS_BEEN_BROUGHT_EARLY);
		
		return new SuccessResult();
	}
	
	
	private RentalDetailDto convertToDto(Rental rental) {
		
		RentalDetailDto rentalDto = modelMapper.map(rental, RentalDetailDto.class);
		return rentalDto;
	}

}
