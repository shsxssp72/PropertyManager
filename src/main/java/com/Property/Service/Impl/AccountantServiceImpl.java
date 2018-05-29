package com.Property.Service.Impl;

import com.Property.Dao.CarIORecordDao;
import com.Property.Dao.ChargingSituationDao;
import com.Property.Dao.StaffDao;
import com.Property.Domain.CarIORecord;
import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Staff;
import com.Property.Service.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountantServiceImpl implements AccountantService
{

	@Autowired
	private ChargingSituationDao chargingSituationDao;
	@Autowired
	private CarIORecordDao carIORecordDao;
	@Autowired
	private StaffDao staffDao;

	@Override
	public List<ChargingSituation> getAllCharging()
	{
		return chargingSituationDao.getAllCharging();
	}

	@Override
	public List<CarIORecord> getExternal()
	{
		return carIORecordDao.getExternal();
	}

	@Override
	public Staff getSelfInfo(String id)
	{
		return staffDao.getSelfInfo(id);
	}

	@Override
	public List<ChargingSituation> getPaymentbyParams(Map<String,Object> params)
	{
		return chargingSituationDao.getPaymentbyParams(params);
	}
}
