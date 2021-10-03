package com.example.ReCapProject.entities.concretes;

import java.time.LocalDateTime;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_images")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "car"})
public class CarImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private int imageId;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "image_date")
	private LocalDateTime date;
	
	@ManyToOne(cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
	@JoinColumn(name = "car_id")
	private Car car;
}
