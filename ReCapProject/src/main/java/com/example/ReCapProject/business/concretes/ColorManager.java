package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.ColorService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.ColorDao;
import com.example.ReCapProject.entities.concretes.Color;
import com.example.ReCapProject.entities.requests.color.CreateColorRequest;
import com.example.ReCapProject.entities.requests.color.DeleteColorRequest;
import com.example.ReCapProject.entities.requests.color.UpdateColorRequest;

@Service
public class ColorManager implements ColorService{

	private ColorDao colorDao;
	
	@Autowired
	public ColorManager(ColorDao colorDao) {
		this.colorDao = colorDao;
	}

	@Override
	public Result add(CreateColorRequest entity) {
		
		var result = BusinessRules.run(checkIfColorExists(entity.getColorName()));
		
		if(result != null)
			return result;
		
		Color color = new Color();
		color.setColorName(entity.getColorName().toLowerCase().trim());
		
		this.colorDao.save(color);
		return new SuccessResult(Messages.COLOR_ADDED);
	}
	
	@Override
	public Result update(UpdateColorRequest entity) {

		var result = BusinessRules.run(checkIfColorExists(entity.getColorName()));
		
		if(result != null)
			return result;
		
		Color color = this.colorDao.getByColorId(entity.getColorId());
		color.setColorName(entity.getColorName().toLowerCase().trim());
		
		this.colorDao.save(color);
		return new SuccessResult(Messages.COLOR_UPDATED);
	}

	@Override
	public Result delete(DeleteColorRequest entity) {
		this.colorDao.deleteById(entity.getColorId());
		return new SuccessResult(Messages.COLOR_DELETED);
	}

	@Override
	public DataResult<List<Color>> getAll() {
		return new SuccessDataResult<List<Color>>(this.colorDao.findAll(),Messages.COLOURS_LISTED);
	}
	
	
	private Result checkIfColorExists(String colorName) {
		
		if(this.colorDao.existsByColorName(colorName.toLowerCase().trim()))
			return new ErrorResult(Messages.COLOR_ALREADY_EXISTS);
		
		return new SuccessResult();
	}

}
