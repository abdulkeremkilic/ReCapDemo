package com.example.ReCapProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.ReCapProject.entities.abstracts.Customer;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer{

	@NotNull
	@Column(name = "company_name")
	private String companyName;
	
	@NotNull
	@Column(name = "tax_number")
	private String taxNumber;

}
