package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CarService;
import com.example.ReCapProject.business.abstracts.DamageRecordService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.DamageRecordDao;
import com.example.ReCapProject.entities.concretes.DamageRecord;
import com.example.ReCapProject.entities.requests.damageRecord.CreateDamageRecordRequest;
import com.example.ReCapProject.entities.requests.damageRecord.DeleteDamageRecordRequest;
import com.example.ReCapProject.entities.requests.damageRecord.UpdateDamageRecordRequest;

@Service
public class DamageRecordManager implements DamageRecordService{

	private DamageRecordDao damageRecordDao;
	
	private CarService carService;
	
	@Autowired
	public DamageRecordManager(DamageRecordDao damageRecordDao, CarService carService) {
		
		this.damageRecordDao = damageRecordDao;
		
		this.carService = carService;
	}

	@Override
	public Result add(CreateDamageRecordRequest entity) {
		
		DamageRecord damageRecord = new DamageRecord();
		damageRecord.setCar(this.carService.getById(entity.getCarId()).getData());
		damageRecord.setRecordInfo(entity.getRecordInfo().trim());
		
		this.damageRecordDao.save(damageRecord);
		return new SuccessResult(Messages.DAMAGE_RECORD_ADDED);
		
	}

	@Override
	public Result update(UpdateDamageRecordRequest entity) {
		
		DamageRecord damageRecord = this.damageRecordDao.getById(entity.getRecordId());
		damageRecord.setRecordInfo(entity.getRecordInfo().trim());
		
		this.damageRecordDao.save(damageRecord);
		return new SuccessResult(Messages.DAMAGE_RECORD_UPDATED);
		
	}

	@Override
	public Result delete(DeleteDamageRecordRequest entity) {
		this.damageRecordDao.deleteById(entity.getRecordId());
		return new SuccessResult(Messages.DAMAGE_RECORD_DELETED);
	}

	@Override
	public DataResult<List<DamageRecord>> getDamageRecordsByCarId(int carId) {
		return new SuccessDataResult<List<DamageRecord>>(this.damageRecordDao.getByCar_CarId(carId), Messages.DAMAGE_RECORD_LISTED);
	}

}
