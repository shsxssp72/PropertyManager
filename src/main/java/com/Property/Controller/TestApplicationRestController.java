package com.Property.Controller;

import com.Property.Entity.SysPermission;
import com.Property.Entity.Ticket;
import com.Property.Mapper.TicketMapper;
import com.Property.Service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
//@Api(value="/Test",tags="测试用页面")
public class TestApplicationRestController
{
	@Autowired
	private TicketMapper ticketMapper;

	//	@ApiOperation(value="测试",notes="仅作为测试用")
	@RequestMapping(value="/ticket/list", method=RequestMethod.GET)
	public ModelAndView index()
	{
		List<Ticket> testLists=ticketMapper.getAll();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);
		return modelAndView;
	}


	@RequestMapping("/display")
	public String tmp()
	{

		String alg="SHA-512";
		Object crd="111";
		Object salt="TestC14E16225482E32714D3831E3E927DE2DDB4F9BF2DB21";
		int iter=5;
		Object result=new SimpleHash(alg,crd,salt,iter);
		System.out.println(result);
		return result.toString();
	}

}
