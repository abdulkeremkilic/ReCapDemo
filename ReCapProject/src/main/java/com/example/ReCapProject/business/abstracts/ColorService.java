package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Color;
import com.example.ReCapProject.entities.dtos.ColorDetailDto;
import com.example.ReCapProject.entities.requests.color.CreateColorRequest;
import com.example.ReCapProject.entities.requests.color.DeleteColorRequest;
import com.example.ReCapProject.entities.requests.color.UpdateColorRequest;

public interface ColorService  {

	Result add(CreateColorRequest entity);
	Result update(UpdateColorRequest entity);
	Result delete(DeleteColorRequest entity);
	
	DataResult<List<ColorDetailDto>> getAllColorDetails();
	
	DataResult<Color> getById(int colorId);
	
	DataResult<List<Color>> getAll();
		
}
