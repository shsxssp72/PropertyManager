package com.Property.Controller;

import com.Property.Entity.RolePermission;
import com.Property.Entity.SysPermission;
import com.Property.Entity.Ticket;
import com.Property.Entity.UserInfo;
import com.Property.Mapper.RolePermissionMapper;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.TicketMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.UserInfoService;
import com.Property.Utility.TableAttrGetter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
//@Api(value="/Test",tags="测试用页面")
public class TestApplicationRestController
{


	@RequestMapping("/display")
	@RequiresPermissions("carpark:all")
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

	@Autowired
	private TicketMapper ticketMapper;

	//	@ApiOperation(value="测试",notes="仅作为测试用")
	@RequestMapping(value="/ticket/list", method=RequestMethod.GET)
	public ModelAndView index()
	{
		List<Ticket> testLists=ticketMapper.getAll();
		List<List<Object>> tmpList=new LinkedList<>();

		for(Ticket a : testLists)
		{
			List<Object> list=new LinkedList<>();
			list.add(a.getTicket_id());
			list.add(a.getTicket_type());
			list.add(a.getTicket_time());
			list.add(a.getInitiator_prprt_id());
			list.add(a.getInitiator_staff_id());
			list.add(a.getSubarea_id());
			list.add(a.getAprt_building());
			list.add(a.getAprt_floor());
			list.add(a.getAprt_room_num());
			list.add(a.getDescription());
			list.add(a.getHandler_id());
			list.add(a.getHandle_time());
			list.add(a.getTicket_result());
			list.add(a.getTicket_fdbk());
			tmpList.add(list);
		}

		TableAttrGetter tableAttrGetter=new TableAttrGetter();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);
		modelAndView.addObject("AttrList",tableAttrGetter.getTicketAttrList());
		modelAndView.addObject("dataList",tmpList);
		return modelAndView;
	}


	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	//	@ApiOperation(value="测试",notes="仅作为测试用")
	@RequestMapping(value="/rp/list", method=RequestMethod.GET)
//	@RequiresPermissions("ticket:view")
	public ModelAndView rp()
	{
		List<RolePermission> testLists=rolePermissionMapper.getAll();
		ModelAndView modelAndView=new ModelAndView("rpdisplay");
		modelAndView.addObject("testLists",testLists);
		return modelAndView;
	}

	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserInfoMapper userInfoMapper;

	@RequestMapping(value={"","/","/index"})
	public ModelAndView Test(HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("index");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
		Calendar calendar=Calendar.getInstance();
		String greeting;
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		if(hour<12)
			greeting="Good Morning! ";
		else if(hour<=17)
			greeting="Good Afternoon! ";
		else
			greeting="Good night! ";
		String date=greeting+"Today is "+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar
				.get(Calendar.DAY_OF_MONTH)+".\n";
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		modelAndView.addObject("date",date);

		return modelAndView;
	}
}
