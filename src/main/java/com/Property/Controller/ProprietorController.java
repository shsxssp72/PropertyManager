package com.Property.Controller;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.ProprietorService;
import io.swagger.annotations.ApiOperation;
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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiresRoles("proprietor")
@RequestMapping("/proprietor")
public class ProprietorController
{

	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	private ProprietorService proprietorService;
	@Autowired
	private ProprietorDao proprietorDao;
	@Autowired
	private ChargingSituationDao chargingSituationDao;
	@Autowired
	private SubareaDao subareaDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private TicketDao ticketDao;

	@ApiOperation(value="业主待缴费项目条目", notes="")
	@RequestMapping("/payment/pay")
	public ModelAndView payment(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("proprietor_payment_pay");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";
		List<ChargingSituation> chargingSituationList=proprietorService.getPayment(prprt_id);

		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		modelAndView.addObject("chargingSituationList",chargingSituationList);
		return modelAndView;
	}

	@RequestMapping("/invoice/{situation_id}")
	public ModelAndView invoice(HttpSession session,@PathVariable String situation_id)
	{
		ModelAndView modelAndView=new ModelAndView("proprietor_invoice");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		String prprt_id="P1000931623";
		params.put("prprt_id",prprt_id);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String tel=proprietors.get(0).getTel();

		Map<String,Object> params1=new HashMap<String,Object>();
		params1.put("situation_id",situation_id);
		List<ChargingSituation> chargingSituationList=proprietorService.getPaymentbyParams(params1);
		ChargingSituation chargingSituation=chargingSituationList.get(0);

		Date now=new Date();
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd");
		String date=myFmt.format(now);

		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		modelAndView.addObject("tel",tel);
		modelAndView.addObject("chargingSituation",chargingSituation);
		modelAndView.addObject("date",date);

		return modelAndView;
	}

	@RequestMapping("/payment/history")
	public ModelAndView history(HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("proprietor_payment_history");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";
		List<ChargingSituation> chargingSituationList=proprietorService.getPaymentHistory(prprt_id);

		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		modelAndView.addObject("chargingSituationList",chargingSituationList);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/new", method=RequestMethod.GET)
	public ModelAndView callRepair(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_new");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";

		modelAndView.addObject("state","get");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/new", method=RequestMethod.POST)
	public ModelAndView callRepairResult(HttpSession session,HttpServletRequest request)
	{

		session.setAttribute("state","post");
		ModelAndView modelAndView=new ModelAndView("ticket_new");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

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
	public ModelAndView repairHistory(HttpSession session)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_history");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";

		List<Ticket> ticketList=proprietorService.getRepairHistory(prprt_id);
		List<Ticket> unfinishedList=new ArrayList<Ticket>();
		List<Ticket> finishedList=new ArrayList<Ticket>();
		for(Ticket ticket : ticketList)
		{
			if(ticket.getInitiator_prprt_id()!=null)
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
		}

		modelAndView.addObject("unfinishedList",unfinishedList);
		modelAndView.addObject("finishedList",finishedList);
		modelAndView.addObject("state","get");
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/ticket/history", method=RequestMethod.POST)
	public ModelAndView repairFeedback(HttpSession session,HttpServletRequest request)
	{

		ModelAndView modelAndView=new ModelAndView("ticket_history");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";

		String ticketId=request.getParameter("ticketId");
		String ticketFdbk=request.getParameter("ticketFdbk");

		//进行工单评价的更新
		System.out.println(ticketId+" "+ticketFdbk);

		List<Ticket> ticketList=proprietorService.getRepairHistory(prprt_id);
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
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";

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
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";

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
		params.put("prprt_name",username);
		List<Proprietor> proprietors=proprietorDao.getProprietorbyParams(params);

		//String prprt_id = proprietors.get(0).getPrprt_id();
		String prprt_id="P1000931623";

		List<Suggestion> suggestions=proprietorService.getAdviceHistory(prprt_id);

		modelAndView.addObject("suggestions",suggestions);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/selfInfo")
	public ModelAndView selfInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//个人信息
		return new ModelAndView();
	}

	@RequestMapping("/alterTel")
	public ModelAndView alterTel(HttpServletRequest request,HttpServletResponse response)
	{
		//更改联系电话
		return new ModelAndView();
	}
}
