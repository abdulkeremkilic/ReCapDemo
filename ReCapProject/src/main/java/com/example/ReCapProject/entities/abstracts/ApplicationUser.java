package com.example.ReCapProject.entities.abstracts;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.ReCapProject.core.entities.concretes.User;
import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.concretes.Rental;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "rentals", "creditCards"})
@Table(name = "application_users")
public class ApplicationUser extends User {
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Rental> rentals;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CreditCard> creditCards;
	
}
