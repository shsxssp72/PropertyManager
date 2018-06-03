package com.Property.Utility;

import java.util.LinkedList;
import java.util.List;

public class TableAttrGetter
{

	private List<String> RolePermissionAttrList=new LinkedList<>();
	private List<String> SysPermissionAttrList=new LinkedList<>();
	private List<String> SysRoleAttrList=new LinkedList<>();
	private List<String> UserInfoAttrList=new LinkedList<>();
	private List<String> UserRoleAttrList=new LinkedList<>();
	private List<String> buildingAttrList=new LinkedList<>();
	private List<String> buildingEntranceRecordAttrList=new LinkedList<>();
	private List<String> carIORecordAttrList=new LinkedList<>();
	private List<String> carparkAttrList=new LinkedList<>();
	private List<String> chargingItemAttrList=new LinkedList<>();
	private List<String> chargingSituationAttrList=new LinkedList<>();
	private List<String> dailyTaskAttrList=new LinkedList<>();
	private List<String> departmentAttrList=new LinkedList<>();
	private List<String> facilitiesAttrList=new LinkedList<>();
	private List<String> feeAttrList=new LinkedList<>();
	private List<String> overhaulRecordAttrList=new LinkedList<>();
	private List<String> proprietorAttrList=new LinkedList<>();
	private List<String> staffAttrList=new LinkedList<>();
	private List<String> subareaAttrList=new LinkedList<>();
	private List<String> suggestionAttrList=new LinkedList<>();
	private List<String> ticketAttrList=new LinkedList<>();


	private int RolePermissionAttrNum;
	private int SysPermissionAttrNum;
	private int SysRoleAttrNum;
	private int UserInfoAttrNum;
	private int UserRoleAttrNum;
	private int buildingAttrNum;
	private int buildingEntranceRecordAttrNum;
	private int carIORecordAttrNum;
	private int carparkAttrNum;
	private int chargingItemAttrNum;
	private int chargingSituationAttrNum;
	private int dailyTaskAttrNum;
	private int departmentAttrNum;
	private int facilitiesAttrNum;
	private int feeAttrNum;
	private int overhaulRecordAttrNum;
	private int proprietorAttrNum;
	private int staffAttrNum;
	private int subareaAttrNum;
	private int suggestionAttrNum;
	private int ticketAttrNum;

	public TableAttrGetter()
	{


		RolePermissionAttrList.add("role_id");
		RolePermissionAttrList.add("perm_id");

		SysPermissionAttrList.add("perm_id");
		SysPermissionAttrList.add("perm_name");
		SysPermissionAttrList.add("resource_type");
		SysPermissionAttrList.add("permit_url");
		SysPermissionAttrList.add("permission");
		SysPermissionAttrList.add("parent_id");
		SysPermissionAttrList.add("parent_ids");
		SysPermissionAttrList.add("is_available");

		SysRoleAttrList.add("role_id");
		SysRoleAttrList.add("role_name");
		SysRoleAttrList.add("descryption");
		SysRoleAttrList.add("is_available");

		UserInfoAttrList.add("uid");
		UserInfoAttrList.add("user_name");
		UserInfoAttrList.add("display_name");
		UserInfoAttrList.add("user_password");
		UserInfoAttrList.add("salt");
		UserInfoAttrList.add("state");

		UserRoleAttrList.add("uid");
		UserRoleAttrList.add("role_id");

		buildingAttrList.add("building_id");
		buildingAttrList.add("subarea_id");
		buildingAttrList.add("max_floor");
		buildingAttrList.add("max_room_num");

		buildingEntranceRecordAttrList.add("entrance_record_id");
		buildingEntranceRecordAttrList.add("prprt_id");
		buildingEntranceRecordAttrList.add("building_id");
		buildingEntranceRecordAttrList.add("access_time");
		buildingEntranceRecordAttrList.add("verify_type");

		carIORecordAttrList.add("io_record_id");
		carIORecordAttrList.add("plate_number");
		carIORecordAttrList.add("prprt_id");
		carIORecordAttrList.add("record_in_time");
		carIORecordAttrList.add("record_out_time");
		carIORecordAttrList.add("price");

		carparkAttrList.add("carpark_id");
		carparkAttrList.add("subarea_id");
		carparkAttrList.add("owner_id");
		carparkAttrList.add("plate_number");
		carparkAttrList.add("valid_term");

		chargingItemAttrList.add("item_id");
		chargingItemAttrList.add("item_title");

		chargingSituationAttrList.add("situation_id");
		chargingSituationAttrList.add("fee_id");
		chargingSituationAttrList.add("prprt_id");
		chargingSituationAttrList.add("collector_id");
		chargingSituationAttrList.add("charge_date");

		dailyTaskAttrList.add("task_id");
		dailyTaskAttrList.add("task_type");
		dailyTaskAttrList.add("task_time");
		dailyTaskAttrList.add("task_area");
		dailyTaskAttrList.add("task_handler");
		dailyTaskAttrList.add("task_result");
		dailyTaskAttrList.add("isException");

		departmentAttrList.add("dept_id");
		departmentAttrList.add("dept_name");

		facilitiesAttrList.add("fclt_id");
		facilitiesAttrList.add("fclt_type");
		facilitiesAttrList.add("subarea_id");
		facilitiesAttrList.add("building_id");
		facilitiesAttrList.add("floor");
		facilitiesAttrList.add("location");

		feeAttrList.add("fee_id");
		feeAttrList.add("item_id");
		feeAttrList.add("start_date");
		feeAttrList.add("end_date");
		feeAttrList.add("price");

		overhaulRecordAttrList.add("overhaul_id");
		overhaulRecordAttrList.add("fclt_id");
		overhaulRecordAttrList.add("overhaul_type");
		overhaulRecordAttrList.add("overhaul_time");
		overhaulRecordAttrList.add("overhaul_handler");
		overhaulRecordAttrList.add("overhaul_result");

		proprietorAttrList.add("prprt_id");
		proprietorAttrList.add("prprt_name");
		proprietorAttrList.add("gender");
		proprietorAttrList.add("tel");
		proprietorAttrList.add("birthday");
		proprietorAttrList.add("aprt_building");
		proprietorAttrList.add("aprt_floor");
		proprietorAttrList.add("aprt_room_num");

		staffAttrList.add("staff_id");
		staffAttrList.add("staff_name");
		staffAttrList.add("gender");
		staffAttrList.add("tel");
		staffAttrList.add("address");
		staffAttrList.add("position");
		staffAttrList.add("salary");
		staffAttrList.add("dept");
		staffAttrList.add("elec_qlfy");
		staffAttrList.add("plbr_qlfy");

		subareaAttrList.add("subarea_id");

		suggestionAttrList.add("suggestion_id");
		suggestionAttrList.add("prprt_id");
		suggestionAttrList.add("suggestion_type");
		suggestionAttrList.add("suggestion_detail");

		ticketAttrList.add("ticket_id");
		ticketAttrList.add("ticket_type");
		ticketAttrList.add("ticket_time");
		ticketAttrList.add("initiator_prprt_id");
		ticketAttrList.add("initiator_staff_id");
		ticketAttrList.add("subarea_id");
		ticketAttrList.add("aprt_building");
		ticketAttrList.add("aprt_floor");
		ticketAttrList.add("aprt_room_num");
		ticketAttrList.add("description");
		ticketAttrList.add("handler_id");
		ticketAttrList.add("handle_time");
		ticketAttrList.add("ticket_result");
		ticketAttrList.add("ticket_fdbk");

		RolePermissionAttrNum=RolePermissionAttrList.size();
		SysPermissionAttrNum=SysPermissionAttrList.size();
		SysRoleAttrNum=SysRoleAttrList.size();
		UserInfoAttrNum=UserInfoAttrList.size();
		UserRoleAttrNum=UserRoleAttrList.size();
		buildingAttrNum=buildingAttrList.size();
		buildingEntranceRecordAttrNum=buildingEntranceRecordAttrList.size();
		carIORecordAttrNum=carIORecordAttrList.size();
		carparkAttrNum=carparkAttrList.size();
		chargingItemAttrNum=chargingItemAttrList.size();
		chargingSituationAttrNum=chargingSituationAttrList.size();
		dailyTaskAttrNum=dailyTaskAttrList.size();
		departmentAttrNum=departmentAttrList.size();
		facilitiesAttrNum=facilitiesAttrList.size();
		feeAttrNum=feeAttrList.size();
		overhaulRecordAttrNum=overhaulRecordAttrList.size();
		proprietorAttrNum=proprietorAttrList.size();
		staffAttrNum=staffAttrList.size();
		subareaAttrNum=subareaAttrList.size();
		suggestionAttrNum=suggestionAttrList.size();
		ticketAttrNum=ticketAttrList.size();
	}

	public List<String> getRolePermissionAttrList()
	{
		return RolePermissionAttrList;
	}

	public List<String> getSysPermissionAttrList()
	{
		return SysPermissionAttrList;
	}

	public List<String> getSysRoleAttrList()
	{
		return SysRoleAttrList;
	}

	public List<String> getUserInfoAttrList()
	{
		return UserInfoAttrList;
	}

	public List<String> getUserRoleAttrList()
	{
		return UserRoleAttrList;
	}

	public List<String> getBuildingAttrList()
	{
		return buildingAttrList;
	}

	public List<String> getBuildingEntranceRecordAttrList()
	{
		return buildingEntranceRecordAttrList;
	}

	public List<String> getCarIORecordAttrList()
	{
		return carIORecordAttrList;
	}

	public List<String> getCarparkAttrList()
	{
		return carparkAttrList;
	}

	public List<String> getChargingItemAttrList()
	{
		return chargingItemAttrList;
	}

	public List<String> getChargingSituationAttrList()
	{
		return chargingSituationAttrList;
	}

	public List<String> getDailyTaskAttrList()
	{
		return dailyTaskAttrList;
	}

	public List<String> getDepartmentAttrList()
	{
		return departmentAttrList;
	}

	public List<String> getFacilitiesAttrList()
	{
		return facilitiesAttrList;
	}

	public List<String> getFeeAttrList()
	{
		return feeAttrList;
	}

	public List<String> getOverhaulRecordAttrList()
	{
		return overhaulRecordAttrList;
	}

	public List<String> getProprietorAttrList()
	{
		return proprietorAttrList;
	}

	public List<String> getStaffAttrList()
	{
		return staffAttrList;
	}

	public List<String> getSubareaAttrList()
	{
		return subareaAttrList;
	}

	public List<String> getSuggestionAttrList()
	{
		return suggestionAttrList;
	}

	public List<String> getTicketAttrList()
	{
		return ticketAttrList;
	}

	public int getRolePermissionAttrNum()
	{
		return RolePermissionAttrNum;
	}

	public int getSysPermissionAttrNum()
	{
		return SysPermissionAttrNum;
	}

	public int getSysRoleAttrNum()
	{
		return SysRoleAttrNum;
	}

	public int getUserInfoAttrNum()
	{
		return UserInfoAttrNum;
	}

	public int getUserRoleAttrNum()
	{
		return UserRoleAttrNum;
	}

	public int getBuildingAttrNum()
	{
		return buildingAttrNum;
	}

	public int getBuildingEntranceRecordAttrNum()
	{
		return buildingEntranceRecordAttrNum;
	}

	public int getCarIORecordAttrNum()
	{
		return carIORecordAttrNum;
	}

	public int getCarparkAttrNum()
	{
		return carparkAttrNum;
	}

	public int getChargingItemAttrNum()
	{
		return chargingItemAttrNum;
	}

	public int getChargingSituationAttrNum()
	{
		return chargingSituationAttrNum;
	}

	public int getDailyTaskAttrNum()
	{
		return dailyTaskAttrNum;
	}

	public int getDepartmentAttrNum()
	{
		return departmentAttrNum;
	}

	public int getFacilitiesAttrNum()
	{
		return facilitiesAttrNum;
	}

	public int getFeeAttrNum()
	{
		return feeAttrNum;
	}

	public int getOverhaulRecordAttrNum()
	{
		return overhaulRecordAttrNum;
	}

	public int getProprietorAttrNum()
	{
		return proprietorAttrNum;
	}

	public int getStaffAttrNum()
	{
		return staffAttrNum;
	}

	public int getSubareaAttrNum()
	{
		return subareaAttrNum;
	}

	public int getSuggestionAttrNum()
	{
		return suggestionAttrNum;
	}

	public int getTicketAttrNum()
	{
		return ticketAttrNum;
	}
}
