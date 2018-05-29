package com.Property.Service.Impl;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService
{

	@Autowired
	private ProprietorDao proprietorDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private ChargingItemDao chargingItemDao;
	@Autowired
	private CarparkDao carparkDao;
	@Autowired
	private FacilitiesDao facilitiesDao;
	@Autowired
	private DailyTaskDao dailyTaskDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private OverhaulRecordDao overhaulRecordDao;
	@Autowired
	private ChargingSituationDao chargingSituationDao;
	@Autowired
	private FeeDao feeDao;
	@Autowired
	private SuggestionDao suggestionDao;
	@Autowired
	private CarIORecordDao carIORecordDao;
	@Autowired
	private BuildingEntranceRecordDao buildingEntranceRecordDao;

	@Override
	public List<Proprietor> getProprietorbyParams(Map<String,Object> params)
	{
		return proprietorDao.getProprietorbyParams(params);
	}

	@Override
	public int addProprietor(Proprietor proprietor)
	{
		return proprietorDao.addProprietor(proprietor);
	}

	@Override
	public int deleteProprietor(String id)
	{
		return proprietorDao.deleteProprietor(id);
	}

	@Override
	public int updateProprietor(Proprietor proprietor)
	{
		return proprietorDao.updateProprietor(proprietor);
	}

	@Override
	public List<Staff> getStaffbyParams(Map<String,Object> params)
	{
		return staffDao.getStaffbyParams(params);
	}

	@Override
	public int addStaff(Staff staff)
	{
		return staffDao.addStaff(staff);
	}

	@Override
	public int deleteStaff(String id)
	{
		return staffDao.deleteStaff(id);
	}

	@Override
	public int updateStaff(Staff staff)
	{
		return staffDao.updateStaff(staff);
	}

	@Override
	public List<Building> getBuildingbyParams(Map<String,Object> params)
	{
		return buildingDao.getBuildingbyParams(params);
	}

	@Override
	public int addBuilding(Building building)
	{
		return buildingDao.addBuilding(building);
	}

	@Override
	public int deleteBuilding(String id)
	{
		return buildingDao.deleteBuilding(id);
	}

	@Override
	public int updateBuilding(Building building)
	{
		return buildingDao.updateBuilding(building);
	}

	@Override
	public List<ChargingItem> getChargingItembyParams(Map<String,Object> params)
	{
		return chargingItemDao.getChargingItembyParams(params);
	}

	@Override
	public int addChargingItem(ChargingItem chargingItem)
	{
		return chargingItemDao.addChargingItem(chargingItem);
	}

	@Override
	public int deleteChargingItem(String id)
	{
		return chargingItemDao.deleteChargingItem(id);
	}

	@Override
	public int updateChargingItem(ChargingItem chargingItem)
	{
		return chargingItemDao.updateChargingItem(chargingItem);
	}

	@Override
	public List<Carpark> getCarparkbyParams(Map<String,Object> params)
	{
		return carparkDao.getCarparkbyParams(params);
	}

	@Override
	public int addCarpark(Carpark carpark)
	{
		return carparkDao.addCarpark(carpark);
	}

	@Override
	public int deleteCarpark(String id)
	{
		return carparkDao.deleteCarpark(id);
	}

	@Override
	public int updateCarpark(Carpark carpark)
	{
		return carparkDao.updateCarpark(carpark);
	}

	@Override
	public List<Facilities> getFacilitiesbyParams(Map<String,Object> params)
	{
		return facilitiesDao.getFacilitiesbyParams(params);
	}

	@Override
	public int addFacility(Facilities facilities)
	{
		return facilitiesDao.addFacility(facilities);
	}

	@Override
	public int deleteFacility(String id)
	{
		return facilitiesDao.deleteFacility(id);
	}

	@Override
	public int updateFacility(Facilities facilities)
	{
		return facilitiesDao.updateFacility(facilities);
	}

	@Override
	public List<DailyTask> getTaskbyParams(Map<String,Object> params)
	{
		return dailyTaskDao.getTaskbyParams(params);
	}

	@Override
	public int addTask(DailyTask dailyTask)
	{
		return dailyTaskDao.addTask(dailyTask);
	}

	@Override
	public int deleteTask(String id)
	{
		return dailyTaskDao.deleteTask(id);
	}

	@Override
	public int updateTask(DailyTask dailyTask)
	{
		return dailyTaskDao.updateTask(dailyTask);
	}

	@Override
	public List<Ticket> getTicketbyParams(Map<String,Object> params)
	{
		return ticketDao.getTicketbyParams(params);
	}

	@Override
	public int addTicket(Ticket ticket)
	{
		return ticketDao.addTicket(ticket);
	}

	@Override
	public int deleteTicket(String id)
	{
		return ticketDao.deleteTicket(id);
	}

	@Override
	public int updateTicket(Ticket ticket)
	{
		return ticketDao.updateTicket(ticket);
	}

	@Override
	public List<OverhaulRecord> getOverhaulbyParams(Map<String,Object> params)
	{
		return overhaulRecordDao.getOverhaulbyParams(params);
	}

	@Override
	public int addOverhaulRecord(OverhaulRecord overhaulRecord)
	{
		return overhaulRecordDao.addOverhaulRecord(overhaulRecord);
	}

	@Override
	public int deleteOverhaulRecord(String id)
	{
		return overhaulRecordDao.deleteOverhaulRecord(id);
	}

	@Override
	public int updateOverhaulRecord(OverhaulRecord overhaulRecord)
	{
		return overhaulRecordDao.updateOverhaulRecord(overhaulRecord);
	}

	@Override
	public List<ChargingSituation> getPaymentbyParams(Map<String,Object> params)
	{
		return chargingSituationDao.getPaymentbyParams(params);
	}

	@Override
	public int addChargingSituation(ChargingSituation chargingSituation)
	{
		return chargingSituationDao.addChargingSituation(chargingSituation);
	}

	@Override
	public int deleteChargingSituation(String id)
	{
		return chargingSituationDao.deleteChargingSituation(id);
	}

	@Override
	public int updateChargingSituation(ChargingSituation chargingSituation)
	{
		return chargingSituationDao.updateChargingSituation(chargingSituation);
	}

	@Override
	public List<Fee> getFeebyParams(Map<String,Object> params)
	{
		return feeDao.getFeebyParams(params);
	}

	@Override
	public int addFee(Fee fee)
	{
		return feeDao.addFee(fee);
	}

	@Override
	public int deleteFee(String id)
	{
		return feeDao.deleteFee(id);
	}

	@Override
	public int updateFee(Fee fee)
	{
		return feeDao.updateFee(fee);
	}

	@Override
	public List<Suggestion> getSuggestionbyParams(Map<String,Object> params)
	{
		return suggestionDao.getSuggestionbyParams(params);
	}

	@Override
	public int addSuggestion(Suggestion suggestion)
	{
		return suggestionDao.addSuggestion(suggestion);
	}

	@Override
	public int deleteSuggestion(String id)
	{
		return suggestionDao.deleteSuggestion(id);
	}

	@Override
	public int updateSuggestion(Suggestion suggestion)
	{
		return suggestionDao.updateSuggestion(suggestion);
	}

	@Override
	public List<CarIORecord> getCarIORecordbyParams(Map<String,Object> params)
	{
		return carIORecordDao.getCarIORecordbyParams(params);
	}

	@Override
	public int addCarIORecord(CarIORecord carIORecord)
	{
		return carIORecordDao.addCarIORecord(carIORecord);
	}

	@Override
	public int deleteCarIORecord(String id)
	{
		return carIORecordDao.deleteCarIORecord(id);
	}

	@Override
	public int updateCarIORecord(CarIORecord carIORecord)
	{
		return carIORecordDao.updateCarIORecord(carIORecord);
	}

	@Override
	public List<BuildingEntranceRecord> getBERbyParams(Map<String,Object> params)
	{
		return buildingEntranceRecordDao.getBERbyParams(params);
	}

	@Override
	public int addBER(BuildingEntranceRecord buildingEntranceRecord)
	{
		return buildingEntranceRecordDao.addBER(buildingEntranceRecord);
	}

	@Override
	public int deleteBER(String id)
	{
		return buildingEntranceRecordDao.deleteBER(id);
	}

	@Override
	public int updateBER(BuildingEntranceRecord buildingEntranceRecord)
	{
		return buildingEntranceRecordDao.updateBER(buildingEntranceRecord);
	}
}
