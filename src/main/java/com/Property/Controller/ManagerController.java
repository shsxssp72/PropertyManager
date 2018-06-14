package com.Property.Controller;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Mapper.*;
import com.Property.Service.ManagerService;
import com.Property.Utility.CryptoUtil;
import com.Property.Utility.TableAttrGetter;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequiresRoles("manager")
@RequestMapping("/manager")
public class ManagerController
{
	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	RolePermissionMapper rolePermissionMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Autowired
	SysPermissionMapper sysPermissionMapper;

	@Autowired
	ManagerService managerService;
	@Autowired
	BuildingDao buildingDao;
	@Autowired
	BuildingEntranceRecordDao buildingEntranceRecordDao;
	@Autowired
	CarIORecordDao carIORecordDao;
	@Autowired
	CarparkDao carparkDao;
	@Autowired
	ChargingItemDao chargingItemDao;
	@Autowired
	ChargingSituationDao chargingSituationDao;
	@Autowired
	DailyTaskDao dailyTaskDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	FacilitiesDao facilitiesDao;
	@Autowired
	FeeDao feeDao;
	@Autowired
	OverhaulRecordDao overhaulRecordDao;
	@Autowired
	ProprietorDao proprietorDao;
	@Autowired
	StaffDao staffDao;
	@Autowired
	SubareaDao subareaDao;
	@Autowired
	SuggestionDao suggestionDao;
	@Autowired
	TicketDao ticketDao;

	CryptoUtil cryptoUtil=new CryptoUtil();


    /*@RequestMapping(value = "/proprietor", method = RequestMethod.PUT)
    public*/


	@RequestMapping(value="/manageData", method=RequestMethod.GET)
	ModelAndView getTest(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView modelAndView=new ModelAndView("dataTable");
		modelAndView.addObject("msg","");
		String username=(String)request.getSession().getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);


		return modelAndView;
	}

	@RequestMapping(value="/manageData", method=RequestMethod.POST)
	ModelAndView postTest(HttpServletRequest request,HttpServletResponse response,String action)
	{
		ModelAndView modelAndView=getTest(request,response);
		TableAttrGetter tableAttrGetter=new TableAttrGetter();


		if(action.equals("Search"))
		{
			String table_name=request.getParameter("table_name");
			if(table_name.equals("building"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getBuildingAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getBuildingAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getBuildingAttrList().get(i),result);
				}


				List<Building> buildingList=buildingDao.getBuildingbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Building each : buildingList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getBuilding_id());
					list.add(each.getSubarea_id());
					list.add(each.getMax_floor());
					list.add(each.getMax_room_num());
					dataList.add(list);
				}

				modelAndView.addObject("AttrList",tableAttrGetter.getBuildingAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getBuildingAttrNum());
			}
			else if(table_name.equals("buildingEntranceRecord"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getBuildingEntranceRecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getBuildingEntranceRecordAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getBuildingEntranceRecordAttrList().get(i),result);
				}


				List<BuildingEntranceRecord> buildingEntranceRecordList=buildingEntranceRecordDao
						.getBERbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(BuildingEntranceRecord each : buildingEntranceRecordList)
				{
					List<Object> list=new LinkedList<>();
					list.add(each.getEntrance_record_id());
					list.add(each.getPrprt_id());
					list.add(each.getBuilding_id());
					list.add(each.getAccess_time());
					list.add(each.getVerify_type());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getBuildingEntranceRecordAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getBuildingEntranceRecordAttrNum());
			}
			else if(table_name.equals("carIORecord"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getCarIORecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getCarIORecordAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getCarIORecordAttrList().get(i),result);
				}


				List<CarIORecord> carIORecordList=carIORecordDao.getCarIORecordbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(CarIORecord each : carIORecordList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getIo_record_id());
					list.add(each.getPlate_number());
					list.add(each.getPrprt_id());
					list.add(each.getRecord_in_time());
					list.add(each.getRecord_out_time());
					list.add(each.getPrice());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getCarIORecordAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getCarIORecordAttrNum());
			}
			else if(table_name.equals("carpark"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getCarparkAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getCarparkAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getCarparkAttrList().get(i),result);
				}


				List<Carpark> carparkList=carparkDao.getCarparkbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Carpark each : carparkList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getCarpark_id());
					list.add(each.getSubarea_id());
					list.add(each.getOwner_id());
					list.add(each.getPlate_number());
					list.add(each.getValid_term());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getCarparkAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getCarparkAttrNum());
			}
			else if(table_name.equals("chargingItem"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getChargingItemAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getChargingItemAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getChargingItemAttrList().get(i),result);
				}


				List<ChargingItem> chargingItemList=chargingItemDao.getChargingItembyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(ChargingItem each : chargingItemList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getItem_id());
					list.add(each.getItem_title());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getChargingItemAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getChargingItemAttrNum());
			}
			else if(table_name.equals("chargingSituation"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getChargingSituationAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getChargingSituationAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getChargingSituationAttrList().get(i),result);
				}


				List<ChargingSituation> chargingSituationList=chargingSituationDao.getPaymentbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(ChargingSituation each : chargingSituationList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getSituation_id());
					list.add(each.getFee_id());
					list.add(each.getPrprt_id());
					list.add(each.getCollector_id());
					list.add(each.getCharge_date());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getChargingSituationAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getChargingSituationAttrNum());
			}
			else if(table_name.equals("dailyTask"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getDailyTaskAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getDailyTaskAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getDailyTaskAttrList().get(i),result);
				}


				List<DailyTask> dailyTaskList=dailyTaskDao.getTaskbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(DailyTask each : dailyTaskList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getTask_id());
					list.add(each.getTask_type());
					list.add(each.getTask_time());
					list.add(each.getTask_area());
					list.add(each.getTask_handler());
					list.add(each.getTask_result());
					list.add(each.isException());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getDailyTaskAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getDailyTaskAttrNum());
			}
			else if(table_name.equals("department"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getDepartmentAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getDepartmentAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getDepartmentAttrList().get(i),result);
				}


				List<Department> departmentList=departmentDao.getDeptbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Department each : departmentList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getDept_id());
					list.add(each.getDept_name());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getDepartmentAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getDepartmentAttrNum());
			}
			else if(table_name.equals("facilities"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getFacilitiesAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getFacilitiesAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getFacilitiesAttrList().get(i),result);
				}


				List<Facilities> facilitiesList=facilitiesDao.getFacilitiesbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Facilities each : facilitiesList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getFclt_id());
					list.add(each.getFclt_type());
					list.add(each.getSubarea_id());
					list.add(each.getBuilding_id());
					list.add(each.getFloor());
					list.add(each.getLocation());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getFacilitiesAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getFacilitiesAttrNum());
			}
			else if(table_name.equals("fee"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getFeeAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getFeeAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getFeeAttrList().get(i),result);
				}


				List<Fee> feeList=feeDao.getFeebyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Fee each : feeList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getFee_id());
					list.add(each.getItem_id());
					list.add(each.getStart_date());
					list.add(each.getEnd_date());
					list.add(each.getPrice());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getFeeAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getFeeAttrNum());
			}
			else if(table_name.equals("overhaulRecord"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getOverhaulRecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getOverhaulRecordAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getOverhaulRecordAttrList().get(i),result);
				}


				List<OverhaulRecord> overhaulRecordList=overhaulRecordDao.getOverhaulbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(OverhaulRecord each : overhaulRecordList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getOverhaul_id());
					list.add(each.getFclt_id());
					list.add(each.getOverhaul_type());
					list.add(each.getOverhaul_time());
					list.add(each.getOverhaul_handler());
					list.add(each.getOverhaul_result());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getOverhaulRecordAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getOverhaulRecordAttrNum());
			}
			else if(table_name.equals("proprietor"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getProprietorAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getProprietorAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getProprietorAttrList().get(i),result);
				}


				List<Proprietor> proprietorList=proprietorDao.getProprietorbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Proprietor each : proprietorList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getPrprt_id());
					list.add(each.getPrprt_name());
					list.add(each.getGender());
					list.add(each.getTel());
					list.add(each.getBirthday());
					list.add(each.getAprt_building());
					list.add(each.getAprt_floor());
					list.add(each.getAprt_room_num());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getProprietorAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getProprietorAttrNum());
			}
			else if(table_name.equals("staff"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getStaffAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getStaffAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getStaffAttrList().get(i),result);
				}


				List<Staff> staffList=staffDao.getStaffbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Staff each : staffList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getStaff_id());
					list.add(each.getStaff_name());
					list.add(each.getGender());
					list.add(each.getTel());
					list.add(each.getAddress());
					list.add(each.getPosition());
					list.add(each.getSalary());
					list.add(each.getDept());
					list.add(each.isElec_qlfy());
					list.add(each.isPlbr_qlfy());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getStaffAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getStaffAttrNum());
			}
			else if(table_name.equals("subarea"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getSubareaAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getSubareaAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getSubareaAttrList().get(i),result);
				}


				List<Subarea> subareaList=subareaDao.getSubareabyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Subarea each : subareaList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getSubarea_id());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getSubareaAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getSubareaAttrNum());
			}
			else if(table_name.equals("suggestion"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getSuggestionAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getSuggestionAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getSuggestionAttrList().get(i),result);
				}


				List<Suggestion> suggestionList=suggestionDao.getSuggestionbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Suggestion each : suggestionList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getSuggestion_id());
					list.add(each.getPrprt_id());
					list.add(each.getSuggestion_type());
					list.add(each.getSuggestion_detail());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getSuggestionAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getSuggestionAttrNum());
			}
			else if(table_name.equals("ticket"))
			{
				Map<String,Object> params=new HashMap<>();
				for(int i=0;i<tableAttrGetter.getTicketAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getTicketAttrList().get(i));
					if(result!=null)
						if(!result.equals(""))
							params.put(tableAttrGetter.getTicketAttrList().get(i),result);
				}


				List<Ticket> ticketList=ticketDao.getTicketbyParams(params);
				List<List<Object>> dataList=new LinkedList<>();
				for(Ticket each : ticketList)
				{

					List<Object> list=new LinkedList<>();
					list.add(each.getTicket_id());
					list.add(each.getTicket_type());
					list.add(each.getTicket_time());
					list.add(each.getInitiator_prprt_id());
					list.add(each.getInitiator_staff_id());
					list.add(each.getSubarea_id());
					list.add(each.getAprt_building());
					list.add(each.getAprt_floor());
					list.add(each.getAprt_room_num());
					list.add(each.getDescription());
					list.add(each.getHandler_id());
					list.add(each.getHandle_time());
					list.add(each.getTicket_result());
					list.add(each.getTicket_fdbk());
					dataList.add(list);
				}


				modelAndView.addObject("AttrList",tableAttrGetter.getTicketAttrList());
				modelAndView.addObject("dataList",dataList);
				modelAndView.addObject("attrNum",tableAttrGetter.getTicketAttrNum());
			}
			modelAndView.addObject("lastQueryTable",table_name);
		}
		else if(action.equals("Update"))
		{
			String update_lastQueryTable=request.getParameter("update_lastQueryTable");
			if(update_lastQueryTable.equals("building"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getBuildingAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getBuildingAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Building building=buildingDao.getByID(update_id);
					building.setSubarea_id(request.getParameter("subarea_id_update"));
					building.setMax_floor(Integer.parseInt(request.getParameter("max_floor_update")));
					building.setMax_room_num(Integer.parseInt(request.getParameter("max_room_num_update")));
					buildingDao.updateBuilding(building);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("buildingEntranceRecord"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getBuildingEntranceRecordAttrNum();i++)
				{
					String result=request
							.getParameter(tableAttrGetter.getBuildingEntranceRecordAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					BuildingEntranceRecord buildingEntranceRecord=buildingEntranceRecordDao.getByID(update_id);
					buildingEntranceRecord.setPrprt_id(request.getParameter("prprt_id_update"));
					buildingEntranceRecord.setBuilding_id(request.getParameter("building_id_update"));
					buildingEntranceRecord
							.setAccess_time(Timestamp.valueOf(request.getParameter("access_time_update")));
					buildingEntranceRecord.setVerify_type(request.getParameter("verify_type_update"));
					buildingEntranceRecordDao.updateBER(buildingEntranceRecord);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("carIORecord"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getCarIORecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getCarIORecordAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					CarIORecord carIORecord=carIORecordDao.getByID(update_id);
					carIORecord.setPlate_number(request.getParameter("plate_number_update"));
					carIORecord.setPrprt_id(request.getParameter("prprt_id_update"));
					carIORecord.setRecord_in_time(Timestamp.valueOf(request.getParameter("record_in_time_update")));
					carIORecord.setRecord_out_time(Timestamp.valueOf(request.getParameter("record_out_time_update")));
					carIORecord.setPrice(Double.parseDouble(request.getParameter("price_update")));
					carIORecordDao.updateCarIORecord(carIORecord);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("carpark"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getCarparkAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getCarparkAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Carpark carpark=carparkDao.getByID(update_id);
					carpark.setSubarea_id(request.getParameter("subarea_id_update"));
					carpark.setOwner_id(request.getParameter("owner_id_update"));
					carpark.setPlate_number(request.getParameter("plate_number_update"));
					carpark.setValid_term(Timestamp.valueOf(request.getParameter("valid_term_update")));
					carparkDao.updateCarpark(carpark);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("chargingItem"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getChargingItemAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getChargingItemAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					ChargingItem chargingItem=chargingItemDao.getByID(update_id);
					chargingItem.setItem_title(request.getParameter("item_title_update"));
					chargingItemDao.updateChargingItem(chargingItem);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("chargingSituation"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getChargingSituationAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getChargingSituationAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					ChargingSituation chargingSituation=chargingSituationDao.getByID(update_id);
					chargingSituation.setFee_id(request.getParameter("fee_id_update"));
					chargingSituation.setPrprt_id(request.getParameter("prprt_id_update"));
					chargingSituation.setCollector_id(request.getParameter("collector_id_update"));
					chargingSituation.setCharge_date(Timestamp.valueOf(request.getParameter("charge_date_update")));
					chargingSituationDao.updateChargingSituation(chargingSituation);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("dailyTask"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getDailyTaskAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getDailyTaskAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					DailyTask dailyTask=dailyTaskDao.getByID(update_id);
					dailyTask.setTask_type(request.getParameter("task_type_update"));
					dailyTask.setTask_time(Timestamp.valueOf(request.getParameter("task_time_update")));
					dailyTask.setTask_area(request.getParameter("task_area_update"));
					dailyTask.setTask_handler(request.getParameter("task_handler_update"));
					dailyTask.setTask_result(request.getParameter("task_result_update"));
					dailyTask.setException(Boolean.parseBoolean(request.getParameter("isException_update")));
					dailyTaskDao.updateTask(dailyTask);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("department"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getDepartmentAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getDepartmentAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Department department=departmentDao.getByID(update_id);
					department.setDept_name(request.getParameter("dept_name_update"));
					departmentDao.updateDepartment(department);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("facilities"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getFacilitiesAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getFacilitiesAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Facilities facilities=facilitiesDao.getByID(update_id);
					facilities.setFclt_type(request.getParameter("fclt_type_update"));
					facilities.setBuilding_id(request.getParameter("building_id_update"));
					facilities.setFloor(Integer.parseInt(request.getParameter("floor_update")));
					facilities.setLocation(request.getParameter("location_update"));
					facilitiesDao.updateFacility(facilities);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("fee"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getFeeAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getFeeAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Fee fee=feeDao.getByID(update_id);
					fee.setItem_id(request.getParameter("item_id_update"));
					fee.setStart_date(Timestamp.valueOf(request.getParameter("start_date_update")));
					fee.setEnd_date(Timestamp.valueOf(request.getParameter("end_date_update")));
					fee.setPrice(Double.parseDouble(request.getParameter("price_update")));
					feeDao.updateFee(fee);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("overhaulRecord"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getOverhaulRecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getOverhaulRecordAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					OverhaulRecord overhaulRecord=overhaulRecordDao.getByID(update_id);
					overhaulRecord.setFclt_id(request.getParameter("fclt_id_update"));
					overhaulRecord.setOverhaul_type(request.getParameter("overhaul_type_update"));
					overhaulRecord.setOverhaul_time(Timestamp.valueOf(request.getParameter("overhaul_time_update")));
					overhaulRecord.setOverhaul_handler(request.getParameter("overhaul_handler_update"));
					overhaulRecord.setOverhaul_result(request.getParameter("overhaul_result_update"));
					overhaulRecordDao.updateOverhaulRecord(overhaulRecord);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("proprietor"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getProprietorAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getProprietorAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Proprietor proprietor=proprietorDao.getByID(update_id);
					proprietor.setPrprt_name(request.getParameter("prprt_name_update"));
					proprietor.setGender(request.getParameter("gender_update"));
					proprietor.setTel(request.getParameter("tel_update"));
					proprietor.setBirthday(Timestamp.valueOf(request.getParameter("birthday_update")));
					proprietor.setAprt_building(request.getParameter("aprt_building_update"));
					proprietor.setAprt_floor(Integer.parseInt(request.getParameter("aprt_floor_update")));
					proprietor.setAprt_room_num(Integer.parseInt(request.getParameter("aprt_room_num_update")));
					proprietorDao.updateProprietor(proprietor);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("staff"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getStaffAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getStaffAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Staff staff=staffDao.getByID(update_id);
					staff.setStaff_name(request.getParameter("staff_name_update"));
					staff.setGender(request.getParameter("gender_update"));
					staff.setTel(request.getParameter("tel_update"));
					staff.setAddress(request.getParameter("address_update"));
					staff.setPosition(request.getParameter("position_update"));
					staff.setSalary(Double.parseDouble(request.getParameter("salary_update")));
					staff.setDept(request.getParameter("dept_update"));
					staff.setElec_qlfy(Boolean.parseBoolean(request.getParameter("elec_qlfy_update")));
					staff.setPlbr_qlfy(Boolean.parseBoolean(request.getParameter("plbr_qlfy_update")));
					staffDao.updateStaff(staff);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("subarea"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getSubareaAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getSubareaAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Subarea subarea=subareaDao.getByID(update_id);


					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("suggestion"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getSuggestionAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getSuggestionAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Suggestion suggestion=suggestionDao.getByID(update_id);
					suggestion.setPrprt_id(request.getParameter("prprt_id_update"));
					suggestion.setSuggestion_type(request.getParameter("suggestion_type_update"));
					suggestion.setSuggestion_detail(request.getParameter("suggestion_detail_update"));
					suggestionDao.updateSuggestion(suggestion);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(update_lastQueryTable.equals("ticket"))
			{
				boolean flag=true;
				for(int i=0;i<tableAttrGetter.getTicketAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getTicketAttrList().get(i)+"_update");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String update_id=request.getParameter("update_id");
					Ticket ticket=ticketDao.getByID(update_id);
					ticket.setTicket_type(request.getParameter("ticket_type_update"));
					ticket.setTicket_time(Timestamp.valueOf(request.getParameter("ticket_time_update")));
					ticket.setInitiator_prprt_id(request.getParameter("initiator_prprt_id_update"));
					ticket.setInitiator_staff_id(request.getParameter("initiator_staff_id_update"));
					ticket.setSubarea_id(request.getParameter("subarea_id_update"));
					ticket.setAprt_building(request.getParameter("aprt_building_update"));
					ticket.setAprt_floor(Integer.parseInt(request.getParameter("aprt_floor_update")));
					ticket.setAprt_room_num(Integer.parseInt(request.getParameter("aprt_room_num_update")));
					ticket.setDescription(request.getParameter("description_update"));
					ticket.setHandler_id(request.getParameter("handler_id_update"));
					ticket.setHandle_time(Timestamp.valueOf(request.getParameter("handle_time_update")));
					ticket.setTicket_result(request.getParameter("ticket_result_update"));
					ticket.setTicket_fdbk(Integer.parseInt(request.getParameter("ticket_fdbk_update")));
					ticketDao.updateTicket(ticket);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}


			modelAndView.addObject("lastQueryTable",update_lastQueryTable);
		}
		else if(action.equals("Delete"))
		{
			String update_lastQueryTable=request.getParameter("update_lastQueryTable");
			if(update_lastQueryTable.equals("building"))
			{
				String update_id=request.getParameter("update_id");
				buildingDao.deleteBuilding(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("buildingEntranceRecord"))
			{
				String update_id=request.getParameter("update_id");
				buildingEntranceRecordDao.deleteBER(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("carIORecord"))
			{
				String update_id=request.getParameter("update_id");
				carIORecordDao.deleteCarIORecord(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("carpark"))
			{
				String update_id=request.getParameter("update_id");
				carparkDao.deleteCarpark(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("chargingItem"))
			{
				String update_id=request.getParameter("update_id");
				chargingItemDao.deleteChargingItem(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("chargingSituation"))
			{
				String update_id=request.getParameter("update_id");
				chargingSituationDao.deleteChargingSituation(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("dailyTask"))
			{
				String update_id=request.getParameter("update_id");
				dailyTaskDao.deleteTask(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("department"))
			{
				String update_id=request.getParameter("update_id");
				departmentDao.deleteDepartment(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("facilities"))
			{
				String update_id=request.getParameter("update_id");
				facilitiesDao.deleteFacility(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("fee"))
			{
				String update_id=request.getParameter("update_id");
				feeDao.deleteFee(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("overhaulRecord"))
			{
				String update_id=request.getParameter("update_id");
				overhaulRecordDao.deleteOverhaulRecord(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("proprietor"))
			{
				String update_id=request.getParameter("update_id");
				proprietorDao.deleteProprietor(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("staff"))
			{
				String update_id=request.getParameter("update_id");
				staffDao.deleteStaff(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("subarea"))
			{
				String update_id=request.getParameter("update_id");
				subareaDao.deleteSubarea(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("suggestion"))
			{
				String update_id=request.getParameter("update_id");
				suggestionDao.deleteSuggestion(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			else if(update_lastQueryTable.equals("ticket"))
			{
				String update_id=request.getParameter("update_id");
				ticketDao.deleteTicket(update_id);
				modelAndView.addObject("isSuccess",1);
				modelAndView.addObject("isFail",0);
			}
			modelAndView.addObject("lastQueryTable",update_lastQueryTable);

		}
		else if(action.equals("Insert"))
		{
			String table_name=request.getParameter("new_lastQueryTable");
			if(table_name.equals("building"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getBuildingAttrNum();i++)//避开id,id由后端生成
				{
					String result=request.getParameter(tableAttrGetter.getBuildingAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="B"+cryptoUtil.getRandomNumber(5);
					for(;buildingDao.getIdCount(id)!=0;)
						id="B"+cryptoUtil.getRandomNumber(5);
					Building building=new Building();
					building.setBuilding_id(id);
					building.setSubarea_id(request.getParameter("subarea_id_new"));
					building.setMax_floor(Integer.parseInt(request.getParameter("max_floor_new")));
					building.setMax_room_num(Integer.parseInt(request.getParameter("max_room_num_new")));
					buildingDao.addBuilding(building);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("buildingEntranceRecord"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getBuildingEntranceRecordAttrNum();i++)
				{
					String result=request
							.getParameter(tableAttrGetter.getBuildingEntranceRecordAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="BER"+cryptoUtil.getRandomNumber(15);
					for(;buildingEntranceRecordDao.getIdCount(id)!=0;)
						id="BER"+cryptoUtil.getRandomNumber(15);
					BuildingEntranceRecord buildingEntranceRecord=new
							BuildingEntranceRecord();
					buildingEntranceRecord.setEntrance_record_id(id);
					buildingEntranceRecord.setPrprt_id(request.getParameter("prprt_id_new"));
					buildingEntranceRecord.setBuilding_id(request.getParameter("building_id_new"));
					buildingEntranceRecord
							.setAccess_time(Timestamp.valueOf(request.getParameter("access_time_new")));
					buildingEntranceRecord.setVerify_type(request.getParameter("verify_type_new"));
					buildingEntranceRecordDao.addBER(buildingEntranceRecord);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("carIORecord"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getCarIORecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getCarIORecordAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="CIOR"+cryptoUtil.getRandomNumber(15);
					for(;carIORecordDao.getIdCount(id)!=0;)
						id="CIOR"+cryptoUtil.getRandomNumber(15);
					CarIORecord carIORecord=new CarIORecord();
					carIORecord.setIo_record_id(id);
					carIORecord.setPlate_number(request.getParameter("plate_number_new"));
					carIORecord.setPrprt_id(request.getParameter("prprt_id_new"));
					carIORecord.setRecord_in_time(Timestamp.valueOf(request.getParameter("record_in_time_new")));
					carIORecord.setRecord_out_time(Timestamp.valueOf(request.getParameter("record_out_time_new")));
					carIORecord.setPrice(Double.parseDouble(request.getParameter("price_new")));
					carIORecordDao.addCarIORecord(carIORecord);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("carpark"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getCarparkAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getCarparkAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="C"+cryptoUtil.getRandomNumber(5);
					for(;carparkDao.getIdCount(id)!=0;)
						id="C"+cryptoUtil.getRandomNumber(5);
					Carpark carpark=new Carpark();
					carpark.setCarpark_id(id);
					carpark.setSubarea_id(request.getParameter("subarea_id_new"));
					carpark.setOwner_id(request.getParameter("owner_id_new"));
					carpark.setPlate_number(request.getParameter("plate_number_new"));
					carpark.setValid_term(Timestamp.valueOf(request.getParameter("valid_term_new")));
					carparkDao.addCarpark(carpark);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("chargingItem"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getChargingItemAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getChargingItemAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="CI"+cryptoUtil.getRandomNumber(5);
					for(;chargingItemDao.getIdCount(id)!=0;)
						id="CI"+cryptoUtil.getRandomNumber(5);
					ChargingItem chargingItem=new ChargingItem();
					chargingItem.setItem_id(id);
					chargingItem.setItem_title(request.getParameter("item_title_new"));
					chargingItemDao.addChargingItem(chargingItem);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("chargingSituation"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getChargingSituationAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getChargingSituationAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="CS"+cryptoUtil.getRandomNumber(15);
					for(;chargingSituationDao.getIdCount(id)!=0;)
						id="CS"+cryptoUtil.getRandomNumber(15);
					ChargingSituation chargingSituation=new ChargingSituation();
					chargingSituation.setSituation_id(id);
					chargingSituation.setFee_id(request.getParameter("fee_id_new"));
					chargingSituation.setPrprt_id(request.getParameter("prprt_id_new"));
					chargingSituation.setCollector_id(request.getParameter("collector_id_new"));
					chargingSituation.setCharge_date(Timestamp.valueOf(request.getParameter("charge_date_new")));
					chargingSituationDao.addChargingSituation(chargingSituation);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("dailyTask"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getDailyTaskAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getDailyTaskAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="DT"+cryptoUtil.getRandomNumber(15);
					for(;dailyTaskDao.getIdCount(id)!=0;)
						id="DT"+cryptoUtil.getRandomNumber(15);
					DailyTask dailyTask=new DailyTask();
					dailyTask.setTask_id(id);
					dailyTask.setTask_type(request.getParameter("task_type_new"));
					dailyTask.setTask_time(Timestamp.valueOf(request.getParameter("task_time_new")));
					dailyTask.setTask_area(request.getParameter("task_area_new"));
					dailyTask.setTask_handler(request.getParameter("task_handler_new"));
					dailyTask.setTask_result(request.getParameter("task_result_new"));
					dailyTask.setException(Boolean.parseBoolean(request.getParameter("isException_new")));
					dailyTaskDao.addTask(dailyTask);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("department"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getDepartmentAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getDepartmentAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="D"+cryptoUtil.getRandomNumber(5);
					for(;departmentDao.getIdCount(id)!=0;)
						id="D"+cryptoUtil.getRandomNumber(5);
					Department department=new Department();
					department.setDept_id(id);
					department.setDept_name(request.getParameter("dept_name_new"));
					departmentDao.addDepartment(department);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("facilities"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getFacilitiesAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getFacilitiesAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="FC"+cryptoUtil.getRandomNumber(15);
					for(;facilitiesDao.getIdCount(id)!=0;)
						id="FC"+cryptoUtil.getRandomNumber(15);
					Facilities facilities=new Facilities();
					facilities.setFclt_id(id);
					facilities.setFclt_type(request.getParameter("fclt_type_new"));
					facilities.setBuilding_id(request.getParameter("building_id_new"));
					facilities.setFloor(Integer.parseInt(request.getParameter("floor_new")));
					facilities.setLocation(request.getParameter("location_new"));
					facilitiesDao.addFacility(facilities);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("fee"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getFeeAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getFeeAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="FE"+cryptoUtil.getRandomNumber(10);
					for(;feeDao.getIdCount(id)!=0;)
						id="FE"+cryptoUtil.getRandomNumber(10);
					Fee fee=new Fee();
					fee.setFee_id(id);
					fee.setItem_id(request.getParameter("item_id_new"));
					fee.setStart_date(Timestamp.valueOf(request.getParameter("start_date_new")));
					fee.setEnd_date(Timestamp.valueOf(request.getParameter("end_date_new")));
					fee.setPrice(Double.parseDouble(request.getParameter("price_new")));
					feeDao.addFee(fee);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("overhaulRecord"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getOverhaulRecordAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getOverhaulRecordAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="O"+cryptoUtil.getRandomNumber(15);
					for(;overhaulRecordDao.getIdCount(id)!=0;)
						id="O"+cryptoUtil.getRandomNumber(15);
					OverhaulRecord overhaulRecord=new OverhaulRecord();
					overhaulRecord.setOverhaul_id(id);
					overhaulRecord.setFclt_id(request.getParameter("fclt_id_new"));
					overhaulRecord.setOverhaul_type(request.getParameter("overhaul_type_new"));
					overhaulRecord.setOverhaul_time(Timestamp.valueOf(request.getParameter("overhaul_time_new")));
					overhaulRecord.setOverhaul_handler(request.getParameter("overhaul_handler_new"));
					overhaulRecord.setOverhaul_result(request.getParameter("overhaul_result_new"));
					overhaulRecordDao.addOverhaulRecord(overhaulRecord);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("proprietor"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getProprietorAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getProprietorAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="P"+cryptoUtil.getRandomNumber(10);
					for(;proprietorDao.getIdCount(id)!=0;)
						id="P"+cryptoUtil.getRandomNumber(10);
					Proprietor proprietor=new Proprietor();
					proprietor.setPrprt_id(id);
					proprietor.setPrprt_name(request.getParameter("prprt_name_new"));
					proprietor.setGender(request.getParameter("gender_new"));
					proprietor.setTel(request.getParameter("tel_new"));
					proprietor.setBirthday(Timestamp.valueOf(request.getParameter("birthday_new")));
					proprietor.setAprt_building(request.getParameter("aprt_building_new"));
					proprietor.setAprt_floor(Integer.parseInt(request.getParameter("aprt_floor_new")));
					proprietor.setAprt_room_num(Integer.parseInt(request.getParameter("aprt_room_num_new")));
					proprietorDao.addProprietor(proprietor);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("staff"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getStaffAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getStaffAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="SF"+cryptoUtil.getRandomNumber(10);
					for(;staffDao.getIdCount(id)!=0;)
						id="SF"+cryptoUtil.getRandomNumber(10);
					Staff staff=new Staff();
					staff.setStaff_id(id);
					staff.setStaff_name(request.getParameter("staff_name_new"));
					staff.setGender(request.getParameter("gender_new"));
					staff.setTel(request.getParameter("tel_new"));
					staff.setAddress(request.getParameter("address_new"));
					staff.setPosition(request.getParameter("position_new"));
					staff.setSalary(Double.parseDouble(request.getParameter("salary_new")));
					staff.setDept(request.getParameter("dept_new"));
					staff.setElec_qlfy(Boolean.parseBoolean(request.getParameter("elec_qlfy_new")));
					staff.setPlbr_qlfy(Boolean.parseBoolean(request.getParameter("plbr_qlfy_new")));
					staffDao.addStaff(staff);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("subarea"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getStaffAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getSubareaAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="SA"+cryptoUtil.getRandomNumber(5);
					for(;subareaDao.getIdCount(id)!=0;)
						id="SA"+cryptoUtil.getRandomNumber(5);
					Subarea subarea=new Subarea();
					subarea.setSubarea_id(id);
					subareaDao.addSubarea(subarea);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("suggestion"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getSuggestionAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getSuggestionAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="SG"+cryptoUtil.getRandomNumber(10);
					for(;suggestionDao.getIdCount(id)!=0;)
						id="SG"+cryptoUtil.getRandomNumber(10);
					Suggestion suggestion=new Suggestion();
					suggestion.setSuggestion_id(id);
					suggestion.setPrprt_id(request.getParameter("prprt_id_new"));
					suggestion.setSuggestion_type(request.getParameter("suggestion_type_new"));
					suggestion.setSuggestion_detail(request.getParameter("suggestion_detail_new"));
					suggestionDao.addSuggestion(suggestion);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			else if(table_name.equals("ticket"))
			{
				boolean flag=true;
				for(int i=1;i<tableAttrGetter.getTicketAttrNum();i++)
				{
					String result=request.getParameter(tableAttrGetter.getTicketAttrList().get(i)+"_new");
					if(result==null)
					{
						flag=false;
						break;
					}
					if(result.equals(""))
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					String id="T"+cryptoUtil.getRandomNumber(10);
					for(;ticketDao.getIdCount(id)!=0;)
						id="T"+cryptoUtil.getRandomNumber(10);
					Ticket ticket=new Ticket();
					ticket.setTicket_id(id);
					ticket.setTicket_type(request.getParameter("ticket_type_new"));
					ticket.setTicket_time(Timestamp.valueOf(request.getParameter("ticket_time_new")));
					ticket.setInitiator_prprt_id(request.getParameter("initiator_prprt_id_new"));
					ticket.setInitiator_staff_id(request.getParameter("initiator_staff_id_new"));
					ticket.setSubarea_id(request.getParameter("subarea_id_new"));
					ticket.setAprt_building(request.getParameter("aprt_building_new"));
					ticket.setAprt_floor(Integer.parseInt(request.getParameter("aprt_floor_new")));
					ticket.setAprt_room_num(Integer.parseInt(request.getParameter("aprt_room_num_new")));
					ticket.setDescription(request.getParameter("description_new"));
					ticket.setHandler_id(request.getParameter("handler_id_new"));
					ticket.setHandle_time(Timestamp.valueOf(request.getParameter("handle_time_new")));
					ticket.setTicket_result(request.getParameter("ticket_result_new"));
					ticket.setTicket_fdbk(Integer.parseInt(request.getParameter("ticket_fdbk_new")));
					ticketDao.addTicket(ticket);
					modelAndView.addObject("isSuccess",1);
					modelAndView.addObject("isFail",0);
				}
				else
				{
					modelAndView.addObject("isSuccess",0);
					modelAndView.addObject("isFail",1);
				}
			}
			modelAndView.addObject("lastQueryTable",table_name);
		}


		return modelAndView;
	}


}
