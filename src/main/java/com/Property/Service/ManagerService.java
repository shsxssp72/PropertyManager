package com.Property.Service;


import com.Property.Domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface ManagerService {

    /*基本的对各表的增删改查*/
    List<Proprietor> getProprietorbyParams(Map<String, Object> params);
    int addProprietor(Proprietor proprietor);
    int deleteProprietor(String id);
    int updateProprietor(Proprietor proprietor);

    List<Staff> getStaffbyParams(Map<String, Object> params);
    int addStaff(Staff staff);
    int deleteStaff(String id);
    int updateStaff(Staff staff);

    List<Building> getBuildingbyParams(Map<String, Object> params);
    int addBuilding(Building building);
    int deleteBuilding(String id);
    int updateBuilding(Building building);

    List<ChargingItem> getChargingItembyParams(Map<String, Object> params);
    int addChargingItem(ChargingItem chargingItem);
    int deleteChargingItem(String id);
    int updateChargingItem(ChargingItem chargingItem);

    List<Carpark> getCarparkbyParams(Map<String, Object> params);
    int addCarpark(Carpark carpark);
    int deleteCarpark(String id);
    int updateCarpark(Carpark carpark);

    List<Facilities> getFacilitiesbyParams(Map<String, Object> params);
    int addFacility(Facilities facilities);
    int deleteFacility(String id);
    int updateFacility(Facilities facilities);

    List<DailyTask> getTaskbyParams(Map<String, Object> params);
    int addTask(DailyTask dailyTask);
    int deleteTask(String id);
    int updateTask(DailyTask dailyTask);

    List<Ticket> getTicketbyParams(Map<String, Object> params);
    int addTicket(Ticket ticket);
    int deleteTicket(String id);
    int updateTicket(Ticket ticket);

    List<OverhaulRecord> getOverhaulbyParams(Map<String, Object> params);
    int addOverhaulRecord(OverhaulRecord overhaulRecord);
    int deleteOverhaulRecord(String id);
    int updateOverhaulRecord(OverhaulRecord overhaulRecord);

    List<ChargingSituation> getPaymentbyParams(Map<String, Object> params);
    int addChargingSituation(ChargingSituation chargingSituation);
    int deleteChargingSituation(String id);
    int updateChargingSituation(ChargingSituation chargingSituation);

    List<Fee> getFeebyParams(Map<String, Object> params);
    int addFee(Fee fee);
    int deleteFee(String id);
    int updateFee(Fee fee);

    List<Suggestion> getSuggestionbyParams(Map<String, Object> params);
    int addSuggestion(Suggestion suggestion);
    int deleteSuggestion(String id);
    int updateSuggestion(Suggestion suggestion);

    List<CarIORecord> getCarIORecordbyParams(Map<String, Object> params);
    int addCarIORecord(CarIORecord carIORecord);
    int deleteCarIORecord(String id);
    int updateCarIORecord(CarIORecord carIORecord);

    List<BuildingEntranceRecord> getBERbyParams(Map<String, Object> params);
    int addBER(BuildingEntranceRecord buildingEntranceRecord);
    int deleteBER(String id);
    int updateBER(BuildingEntranceRecord buildingEntranceRecord);
}
