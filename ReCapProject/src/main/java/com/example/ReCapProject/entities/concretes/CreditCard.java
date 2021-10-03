package com.example.ReCapProject.entities.concretes;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.example.ReCapProject.entities.abstracts.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "receipts"})
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private int cardId;
	
	@Column(name = "card_beholder_name")
	private String cardBeholderName;
	
	@Column(name = "card_name")
	private String cardName;
	
	@Column(name = "credit_card_number")
	@CreditCardNumber(ignoreNonDigitCharacters = true)
	private String creditCardNumber;
	
	@Column(name = "cvv_code")
	private String cvvCode;
	
	@Column(name = "expire_date")
	private String expireDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private ApplicationUser user;	
	
}
