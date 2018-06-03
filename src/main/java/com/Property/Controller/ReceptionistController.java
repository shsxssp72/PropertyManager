package com.Property.Controller;

import com.Property.Dao.*;
import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Fee;
import com.Property.Domain.Suggestion;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.ReceptionistService;
import com.Property.Utility.CryptoUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiresRoles("receptionist")
@RequestMapping("/receptionist")
public class ReceptionistController
{

	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	ReceptionistService receptionistService;
	@Autowired
	ChargingSituationDao chargingSituationDao;
	@Autowired
	FeeDao feeDao;
	@Autowired
	StaffDao staffDao;
	@Autowired
	ProprietorDao proprietorDao;
	@Autowired
	SuggestionDao suggestionDao;


	@RequestMapping(value="/updateChargingSituation", method=RequestMethod.GET)
	public ModelAndView getUpdateChargingSituation(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView modelAndView=new ModelAndView("receptionist_payment");
		modelAndView.addObject("msg","");
		String username=(String)request.getSession().getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);

		//面对面缴费
		return modelAndView;
	}

	@RequestMapping(value="/updateChargingSituation", method=RequestMethod.POST)
	public ModelAndView postUpdateChargingSituation(HttpServletRequest request,HttpServletResponse response,
													String action)
	{
		ModelAndView modelAndView=getUpdateChargingSituation(request,response);
		String username=(String)request.getSession().getAttribute("username");

		String prprt_name=request.getParameter("prprt_name");
		if(action.equals("Update"))
		{
			String CSID=request.getParameter("CSID");
			String passLastQuery=request.getParameter("passLastQuery");

			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=df.format(Calendar.getInstance().getTime());
			Timestamp ts=Timestamp.valueOf(time);
			ChargingSituation chargingSituation=chargingSituationDao.getByID(CSID);
			chargingSituation.setCharge_date(ts);
			chargingSituation.setCollector_id(userInfoMapper.getByUserName(username).getStaff_id());
			chargingSituationDao.updateChargingSituation(chargingSituation);
			prprt_name=passLastQuery;
		}


		List<ChargingSituation> chargingSituationList=chargingSituationDao
				.getUnfinishedByUserId(proprietorDao.getIDByName(prprt_name));
		List<List<Object>> dataList=new LinkedList<>();
		for(ChargingSituation each : chargingSituationList)
		{
			List<Object> list=new LinkedList<>();
			list.add(each.getSituation_id());
			list.add(chargingSituationDao.getChargingItemNameByCSID(each.getSituation_id()));
			Fee fee=feeDao.getByID(each.getFee_id());
			list.add(fee.getStart_date().toString()+"/"+fee.getEnd_date().toString());
			list.add(prprt_name);
			list.add(staffDao.getNameByID(each.getCollector_id()));
			list.add("");
			dataList.add(list);
		}

		modelAndView.addObject("dataList",dataList);
		modelAndView.addObject("lastQuery",prprt_name);

		//面对面缴费

		return modelAndView;
	}

	@RequestMapping(value="/createAdvice", method=RequestMethod.GET)
	public ModelAndView getCreateAdvice(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView modelAndView=new ModelAndView("receptionist_suggestion");
		modelAndView.addObject("msg","");
		String username=(String)request.getSession().getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		//面对面意见
		return modelAndView;
	}

	@RequestMapping(value="/createAdvice", method=RequestMethod.POST)
	public ModelAndView postCreateAdvice(HttpServletRequest request,HttpServletResponse response)
	{
		//不可匿名
		ModelAndView modelAndView=getCreateAdvice(request,response);
		String prprt_name=request.getParameter("prprt_name");
		String suggestion_detail=request.getParameter("detail");
		String suggestion_type=request.getParameter("suggestion_type");

		String prprt_id=proprietorDao.getIDByName(prprt_name);
		CryptoUtil cryptoUtil=new CryptoUtil();
		String suggestion_id="SG"+cryptoUtil.getRandomNumber(10);
		for(;suggestionDao.getIdCount(suggestion_id)!=0;)
			suggestion_id="SG"+cryptoUtil.getRandomNumber(10);

		Suggestion suggestion=new Suggestion();
		suggestion.setPrprt_id(prprt_id);
		suggestion.setSuggestion_detail(suggestion_detail);
		suggestion.setSuggestion_id(suggestion_id);
		suggestion.setSuggestion_type(suggestion_type);
		suggestionDao.addSuggestion(suggestion);

		modelAndView.addObject("isSuccess",1);

		//TODO 页面给出提示
		//面对面意见
		return modelAndView;
	}

	@RequestMapping("/selfInfo")
	public ModelAndView selfInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//查看个人信息
		return new ModelAndView();
	}
}
