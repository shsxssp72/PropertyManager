package com.Property.Controller;

import com.Property.Entity.Building;
import com.Property.Entity.Ticket;
import com.Property.Mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestApplicationController
{
	@Autowired
	private TicketMapper ticketMapper;
	@RequestMapping("/Test")
	public ModelAndView index()
	{
		List<Ticket> testLists=ticketMapper.getAll();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);
		return modelAndView;


	}
}
