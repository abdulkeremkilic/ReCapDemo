package com.example.ReCapProject.entities.concretes;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receipt")
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "receipt_id")
	private int receiptId;
	
	@Column(name = "credit_card_number")
	private String creditCardNumber;
	
	@Column(name = "card_beholder_name")
	private String cardBeholderName;
	
	@Column(name = "brand_name")
	private String brandName;
	
	@Column(name = "model_name")
	private String modelName;
	
	@Column(name = "model_year")
	private int modelYear;
	
	@Column(name = "receive_kilometer")
	private long recievedByCustomerKm;
	
	@Column(name = "receipt_date")
	private Date receiptDate;
	
}
