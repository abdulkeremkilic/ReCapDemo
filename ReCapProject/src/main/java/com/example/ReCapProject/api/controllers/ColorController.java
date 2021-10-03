package com.example.ReCapProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.ColorService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Color;
import com.example.ReCapProject.entities.requests.color.CreateColorRequest;
import com.example.ReCapProject.entities.requests.color.DeleteColorRequest;

@RestController
@RequestMapping("/api/colors")
@CrossOrigin
public class ColorController {

	private ColorService colorService;
	
	@Autowired
	public ColorController(ColorService colorService) {
		this.colorService = colorService;
	}

	@PostMapping("/add")
	public Result add(@Valid CreateColorRequest color) {
		return this.colorService.add(color);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@Valid DeleteColorRequest colorId) {
		return this.colorService.delete(colorId);
	}
	
	@GetMapping("/getall")
	public DataResult<List<Color>> getAll() {
		return this.colorService.getAll();
	}
	
}
