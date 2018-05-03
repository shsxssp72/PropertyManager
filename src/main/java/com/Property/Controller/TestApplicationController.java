package com.Property.Controller;

import com.Property.Entity.Building;
import com.Property.Entity.Ticket;
import com.Property.Mapper.TicketMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Api(value="/Test",tags="测试用页面")
public class TestApplicationController
{
	@Autowired
	private TicketMapper ticketMapper;

	@ApiOperation(value="测试",notes="仅作为测试用")
	@RequestMapping(value="/Test",method=RequestMethod.GET)
	public ModelAndView index()
	{
		List<Ticket> testLists=ticketMapper.getAll();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);
		return modelAndView;


	}
}
