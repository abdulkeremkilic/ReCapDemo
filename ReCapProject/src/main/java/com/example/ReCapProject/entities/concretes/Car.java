package com.example.ReCapProject.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "rentals", "carMaintenances", "damageRecords"})
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private int carId;
	
	@Column(name = "daily_price")
	private double dailyPrice;
	
	@Column(name = "min_findex_point")
	private double minFindexPoint;
	
	@Column(name = "total_kilometer")
	private long currentKilometer;
		
	@Nullable
	@Column(name = "is_available", columnDefinition = "boolean default true")
	private boolean isAvailable = true;
	
	@NotNull
	@NotBlank
	@Column(name = "description")
	private String description;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "color_id")
	private Color color;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id")
	private City city;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<Rental> rentals;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<CarImage> carImages;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<Maintenance> carMaintenances;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<DamageRecord> damageRecords;
}
