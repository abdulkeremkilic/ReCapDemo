package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.dtos.CreditCardDto;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {

	CreditCard getByCardName(String cardName);
	CreditCard getByCreditCardNumber(String cardNumber);
	
	@Query("Select new com.example.ReCapProject.entities.dtos.CreditCardDto"
	  		+ " (u.userId, c.cardBeholderName, c.cardName, c.creditCardNumber, c.cvvCode, c.expireDate)"
	  		+ " From CreditCard c Inner Join c.user u"
	  		+ " Where userId =: userId")
	List<CreditCardDto> getCreditCardDetails(int userId);
}
