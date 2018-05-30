package com.Property.Controller;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.CleanService;
import com.Property.Service.OverhaulService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiresRoles("repairman")
@RequestMapping("/repairman")
public class OverhaulController
{

	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	private SubareaDao subareaDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private SuggestionDao suggestionDao;
	@Autowired
	private OverhaulRecordDao overhaulRecordDao;
	@Autowired
	private OverhaulService overhaulService;

	@RequestMapping(value="/overhaul/tbd", method=RequestMethod.GET)
	public ModelAndView tbdOverhaul(HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("overhaul_tbd");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1325604751";

		List<OverhaulRecord> overhaulRecordList=overhaulService.tbdOverhaul(staff_id);

		modelAndView.addObject("overhaulRecordList",overhaulRecordList);
		modelAndView.addObject("state","get");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/overhaul/finish/{overhaul_id}")
	public ModelAndView finishOverhaul(HttpSession session,@PathVariable String overhaul_id)
	{
		ModelAndView modelAndView=new ModelAndView("overhaul_tbd");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1325604751";

		params.clear();
		params.put("overhaul_id",overhaul_id);
		List<OverhaulRecord> overhaulRecords=overhaulRecordDao.getOverhaulbyParams(params);

		modelAndView.addObject("overhaul_id",overhaul_id);
		modelAndView.addObject("overhaul_type",overhaulRecords.get(0).getOverhaul_type());
		modelAndView.addObject("facility_type",overhaulRecords.get(0).getFacilities().getFclt_type());
		modelAndView.addObject("location",overhaulRecords.get(0).getFacilities().getLocation());
		modelAndView.addObject("state","finish");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/overhaul/tbd", method=RequestMethod.POST)
	public ModelAndView finishTaskResult(HttpSession session,HttpServletRequest request)
	{
		ModelAndView modelAndView=new ModelAndView("overhaul_tbd");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1325604751";

		List<OverhaulRecord> overhaulRecordList=overhaulService.tbdOverhaul(staff_id);

		String overhaulID=request.getParameter("overhaulId");
		String overhaulResult=request.getParameter("overhaulResult");
		System.out.println(overhaulID+" "+overhaulResult);
		//完成检修任务结果信息

		modelAndView.addObject("overhaulRecordList",overhaulRecordList);
		modelAndView.addObject("state","post");
		modelAndView.addObject("username-",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/overhaul/finished")
	public ModelAndView historyTask(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("overhaul_finished");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1325604751";

		List<OverhaulRecord> overhaulRecordList=overhaulService.overhaulHistory(staff_id);

		modelAndView.addObject("overhaulRecordList",overhaulRecordList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/tbd", method=RequestMethod.GET)
	public ModelAndView tbdTicket(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_tbd");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		List<Ticket> ticketList=overhaulService.getHistoryFinished(staff_id);

		modelAndView.addObject("state","get");
		modelAndView.addObject("ticketList",ticketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/ticket/finish/{ticket_id}")
	public ModelAndView finishingTicket(HttpSession session,@PathVariable String ticket_id)
	{
		ModelAndView modelAndView=new ModelAndView("ticket_tbd");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		List<Ticket> ticketList=overhaulService.getHistoryFinished(staff_id);

		params.clear();
		params.put("ticket_id",ticket_id);
		List<Ticket> tickets=ticketDao.getTicketbyParams(params);

		modelAndView.addObject("ticket_id",ticket_id);
		modelAndView.addObject("description",tickets.get(0).getDescription());
		modelAndView.addObject("state","finish");
		modelAndView.addObject("ticketList",ticketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/tbd", method=RequestMethod.POST)
	public ModelAndView finishTicket(HttpSession session,HttpServletRequest request)
	{
		ModelAndView modelAndView=new ModelAndView("ticket_tbd");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		String ticketId=request.getParameter("ticketId");
		String ticketResult=request.getParameter("ticketResult");
		//进行ticket结果更新
		System.out.println(ticketId+" "+ticketResult);

		List<Ticket> ticketList=overhaulService.tbdTicket(staff_id);

		modelAndView.addObject("state","post");
		modelAndView.addObject("ticketList",ticketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/ticket/finished")
	public ModelAndView finishHistory(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_finished");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		List<Ticket> ticketList=overhaulService.getHistoryFinished(staff_id);

		modelAndView.addObject("ticketList",ticketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/new", method=RequestMethod.GET)
	public ModelAndView createTicket(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_new");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		modelAndView.addObject("state","get");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/new", method=RequestMethod.POST)
	public ModelAndView createTicketResult(HttpSession session,HttpServletRequest request)
	{

		session.setAttribute("state","post");
		ModelAndView modelAndView=new ModelAndView("ticket_new");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		String ticketType=request.getParameter("ticketType");
		String proprietor=request.getParameter("initiator");
		String subarea=request.getParameter("subarea");
		String building=request.getParameter("building");
		String floor=request.getParameter("floor");
		String room=request.getParameter("room");
		String description=request.getParameter("description");

		System.out.println(ticketType+" "+proprietor+" "+subarea+" "+building+" "+floor+" "+room+" "+description);

		Boolean validation=true;                                                          //判断输入地址信息是否合法
		params.clear();
		params.put("subarea_id",subarea);
		List<Subarea> subareas=subareaDao.getSubareabyParams(params);
		if(subareas.size()==0)
			validation=false;
		else
		{
			params.clear();
			params.put("building_id",building);
			List<Building> buildings=buildingDao.getBuildingbyParams(params);
			if(buildings.size()==0)
				validation=false;
			else
			{
				if(Integer.parseInt(floor)>buildings.get(0).getMax_floor())
					validation=false;
				else
				{
					if(Integer.parseInt(room)>buildings.get(0).getMax_room_num())
						validation=false;
				}
			}
		}

		if(validation)
		{
			//进行ticket的插入
			//进行新收费项的生成
		}

		String modalIcon;
		String modalTitle;
		String modalContent;
		if(validation)
		{
			modalIcon="fa fa-check-circle modal-icon";
			modalTitle="New Ticket Successful";
			modalContent="You have successfully created a new ticket, now you can go to the Ticket History part"+
					" to check the state of your ticket";
		}
		else
		{
			modalIcon="fa fa-exclamation-circle modal-icon";
			modalTitle="New Ticket Failed";
			modalContent="Failed to create a new ticket, there may be something wrong with the location information,"+
					" you can check it out";
		}


		modelAndView.addObject("state","post");
		modelAndView.addObject("modalIcon",modalIcon);
		modelAndView.addObject("modalTitle",modalTitle);
		modelAndView.addObject("modalContent",modalContent);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/history", method=RequestMethod.GET)
	public ModelAndView createHistory(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_history");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		List<Ticket> ticketList=overhaulService.getHistoryCreated(staff_id);
		List<Ticket> unfinishedList=new ArrayList<Ticket>();
		List<Ticket> finishedList=new ArrayList<Ticket>();
		for(Ticket ticket : ticketList)
		{
			if(ticket.getTicket_result()==null)
			{
				unfinishedList.add(ticket);
			}
			else
			{
				finishedList.add(ticket);
			}
		}

		modelAndView.addObject("unfinishedList",unfinishedList);
		modelAndView.addObject("finishedList",finishedList);
		modelAndView.addObject("state","get");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/history", method=RequestMethod.POST)
	public ModelAndView createFeedback(HttpSession session,HttpServletRequest request)
	{
		ModelAndView modelAndView=new ModelAndView("ticket_history");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		String ticketId=request.getParameter("ticketId");
		String ticketFdbk=request.getParameter("ticketFdbk");

		//进行工单评价的更新
		System.out.println(ticketId+" "+ticketFdbk);

		List<Ticket> ticketList=overhaulService.getHistoryCreated(staff_id);
		List<Ticket> unfinishedList=new ArrayList<Ticket>();
		List<Ticket> finishedList=new ArrayList<Ticket>();
		for(Ticket ticket : ticketList)
		{
			if(ticket.getTicket_result()==null)
			{
				unfinishedList.add(ticket);
			}
			else
			{
				finishedList.add(ticket);
			}
		}

		modelAndView.addObject("unfinishedList",unfinishedList);
		modelAndView.addObject("finishedList",finishedList);
		modelAndView.addObject("state","post");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/suggestion/new", method=RequestMethod.GET)
	public ModelAndView giveAdvice(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("suggestion_new");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		modelAndView.addObject("state","get");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/suggestion/new", method=RequestMethod.POST)
	public ModelAndView sendAdvice(HttpSession session,HttpServletRequest request)
	{
		ModelAndView modelAndView=new ModelAndView("suggestion_new");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		String anonymous[]=request.getParameterValues("anonymous");
		String name;
		if(anonymous!=null)
		{
			name="anonymous";
		}
		else
		{
			name=username;
		}

		String suggestionType=request.getParameter("suggestionType");
		String suggestionDetail=request.getParameter("suggestionDetail");

		//提出意见
		System.out.println(suggestionType+" "+name+" "+suggestionDetail);

		modelAndView.addObject("state","post");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/suggestion/history")
	public ModelAndView adviceHistory(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("suggestion_history");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("staff_name",username);
		List<Staff> staffList=staffDao.getStaffbyParams(params);

		//String staff_id = staffList.get(0).getStaff_id();
		String staff_id="SF1040003242";

		params.clear();
		params.put("prprt_id",staff_id);
		List<Suggestion> suggestions=suggestionDao.getSuggestionbyParams(params);

		modelAndView.addObject("suggestions",suggestions);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/selfInfo")
	public ModelAndView selfInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//查看个人信息
		return new ModelAndView();
	}
}
