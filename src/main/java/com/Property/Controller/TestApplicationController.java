package com.Property.Controller;

import com.Property.Dao.TicketDao;
import com.Property.Domain.Fee;
import com.Property.Domain.Ticket;
import com.Property.Service.ProprietorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestApplicationController
{
	@Autowired
	private TicketDao ticketMapper;
//	private ProprietorService propertyService;

	@RequestMapping("/Test")
	public ModelAndView index()
	{
		List<Ticket> testLists=ticketMapper.getAll();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);

//		List<Fee> feeList = propertyService.getFee();
//		ModelAndView modelAndView = new ModelAndView("dashboard_Test");
//		modelAndView.addObject("feeList",feeList);
		return modelAndView;
	}
}
