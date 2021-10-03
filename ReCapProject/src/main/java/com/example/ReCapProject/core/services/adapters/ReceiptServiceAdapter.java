package com.example.ReCapProject.core.services.adapters;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.ReceiptService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.services.CreateReceiptService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.dataAccess.abstracts.CarDao;
import com.example.ReCapProject.dataAccess.abstracts.ReceiptDao;
import com.example.ReCapProject.entities.concretes.Receipt;
import com.example.ReCapProject.entities.requests.creditCard.CreateCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.CreditCardInfoRequest;
import com.example.ReCapProject.entities.requests.rental.CreateRentalRequest;
import com.example.ReCapProject.entities.requests.rental.RentalInfoRequest;

@Service
public class ReceiptServiceAdapter implements ReceiptService {

	private CreateReceiptService createReceiptService;
	private CarDao carDao;
	private ReceiptDao receiptDao;
	
	public ReceiptServiceAdapter(CarDao carDao, ReceiptDao receiptDao) {
		this.carDao = carDao;
		this.receiptDao = receiptDao;
		createReceiptService = new CreateReceiptService();
	}

	@Override
	public DataResult<Receipt> createReceipt(CreateCreditCardRequest creditCard, CreateRentalRequest rental) {
		
		CreditCardInfoRequest creditCardInfo = new CreditCardInfoRequest();
		creditCardInfo.setCardBeholderName(creditCard.getCardBeholderName());
		creditCardInfo.setCardNumber(creditCard.getCardNumber());
		
		RentalInfoRequest rentalInfo = new RentalInfoRequest();
		rentalInfo.setBrandName(this.carDao.getById(rental.getCarId()).getBrand().getBrandName());
		rentalInfo.setModelName(this.carDao.getByCarId(rental.getCarId()).getBrand().getModelName());
		rentalInfo.setModelYear(this.carDao.getByCarId(rental.getCarId()).getBrand().getModelYear());
		rentalInfo.setReceivedByCustomerKm(this.carDao.getByCarId(rental.getCarId()).getCurrentKilometer());

		rentalInfo.setRentDate(rental.getRentDate());
		
		Receipt receipt = this.createReceiptService.createReceipt(creditCardInfo, rentalInfo).getData();
		this.receiptDao.save(receipt);
		
		return this.createReceiptService.createReceipt(creditCardInfo, rentalInfo);
		
	}

	@Override
	public DataResult<List<Receipt>> getReceiptsByDate(Date endDate, Date rentDate) {
		return new SuccessDataResult<List<Receipt>>(this.receiptDao.getAllByReceiptDateLessThanEqualAndReceiptDateGreaterThanEqual(endDate, rentDate), Messages.RECEIPTS_LISTED);
			
	}

	@Override
	public DataResult<List<Receipt>> getByCardBeholderName(String name) {
		return new SuccessDataResult<List<Receipt>>(this.receiptDao.getByCardBeholderName(name), Messages.RECEIPTS_LISTED);
	}

	@Override
	public DataResult<List<Receipt>> getAll() {
		return new SuccessDataResult<List<Receipt>>(this.receiptDao.findAll(), Messages.RECEIPTS_LISTED);
	}

	
}
