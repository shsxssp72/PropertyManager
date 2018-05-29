package com.Property.Controller;

import com.Property.Service.GuardService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiresRoles("guard")
@RequestMapping("/guard")
public class GuardController
{

	@Autowired
	private GuardService guardService;

	@RequestMapping("/tbdTask")
	public ModelAndView tbdTask(HttpServletRequest request,HttpServletResponse response)
	{
		//查看待完成任务
		return new ModelAndView();
	}

	@RequestMapping("/finishTask")
	public ModelAndView finishTask(HttpServletRequest request,HttpServletResponse response)
	{
		//完成任务
		return new ModelAndView();
	}

	@RequestMapping("/historyTask")
	public ModelAndView historyTask(HttpServletRequest request,HttpServletResponse response)
	{
		//查看历史任务
		return new ModelAndView();
	}

	@RequestMapping("/carIO")
	public ModelAndView carIO(HttpServletRequest request,HttpServletResponse response)
	{
		//查看车辆进出记录
		return new ModelAndView();
	}

	@RequestMapping("/externalCar")
	public ModelAndView externalCar(HttpServletRequest request,HttpServletResponse response)
	{
		//查看外来车辆记录
		return new ModelAndView();
	}

	@RequestMapping("/tbdTicket")
	public ModelAndView tbdTicket(HttpServletRequest request,HttpServletResponse response)
	{
		//查看待完成工单
		return new ModelAndView();
	}

	@RequestMapping("/finishTicket")
	public ModelAndView finishTicket(HttpServletRequest request,HttpServletResponse response)
	{
		//完成工单
		return new ModelAndView();
	}

	@RequestMapping("/finishHistory")
	public ModelAndView finishHistory(HttpServletRequest request,HttpServletResponse response)
	{
		//完成工单历史
		return new ModelAndView();
	}

	@RequestMapping("/createTicket")
	public ModelAndView createTicket(HttpServletRequest request,HttpServletResponse response)
	{
		//发起工单
		return new ModelAndView();
	}

	@RequestMapping("/createHistory")
	public ModelAndView createHistory(HttpServletRequest request,HttpServletResponse response)
	{
		//发起工单历史
		return new ModelAndView();
	}

	@RequestMapping("/selfInfo")
	public ModelAndView selfInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//查看个人信息
		return new ModelAndView();
	}
}
