package com.Property.Controller;

import com.Property.Dao.DailyTaskDao;
import com.Property.Dao.OverhaulRecordDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.DailyTask;
import com.Property.Domain.OverhaulRecord;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.CleanService;
import com.Property.Service.GuardService;
import com.Property.Service.OverhaulService;
import com.Property.Service.SupervisorService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
@RequiresRoles("supervisor")
@RequestMapping("/supervisor")
public class SupervisorController
{

	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private DailyTaskDao dailyTaskDao;
	@Autowired
	private OverhaulRecordDao overhaulRecordDao;
	@Autowired
	private SupervisorService supervisorService;
	@Autowired
	private GuardService guardService;
	@Autowired
	private CleanService cleanService;
	@Autowired
	private OverhaulService overhaulService;

	@RequestMapping("/deptStaff")
	public ModelAndView deptStaff(HttpSession session)
	{
		ModelAndView modelAndView = new ModelAndView("department_staff");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		//String dept_id = staff.get(0).getDept();

		List<Staff> staffs = supervisorService.getDeptStaff("D58703");
		List<Staff> staffList = new ArrayList<>();
		for (int i=0;i<staffs.size();i++){
			if (staffs.get(i).getPosition()!="supervisor"){
				staffList.add(staffs.get(i));
			}
		}

		modelAndView.addObject("staffList",staffList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value = "/ticket/{employee_id}", method = RequestMethod.GET)
	public ModelAndView ticketSchedule(HttpSession session, @PathVariable String employee_id){

		ModelAndView modelAndView = new ModelAndView("ticket_schedule");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);
		//String dept_id = staff.get(0).getDept();

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		String dept = "guard";

		List<Ticket> tbdTicketList = new ArrayList<>();
		List<Ticket> finishedTicketList = new ArrayList<>();
		if (dept == "guard"){
			tbdTicketList = guardService.tbdTicket(employee_id);
			finishedTicketList = guardService.getHistoryFinished(employee_id);
		}

		tbdTicketList = finishedTicketList;

		modelAndView.addObject("state", "get");
		modelAndView.addObject("employee_id",employee_id);
		modelAndView.addObject("tbdTicketList", tbdTicketList);
		modelAndView.addObject("finishedTicketList", finishedTicketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/ticket/{employee_id}/{ticket_id}")
	public ModelAndView ticketChange(HttpSession session, @PathVariable String employee_id, @PathVariable String ticket_id){

		ModelAndView modelAndView = new ModelAndView("ticket_schedule");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);
		//String dept_id = staff.get(0).getDept();

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		String dept = "guard";

		List<Ticket> tbdTicketList = new ArrayList<>();
		List<Ticket> finishedTicketList = new ArrayList<>();
		if (dept == "guard"){
			tbdTicketList = guardService.tbdTicket(employee_id);
			finishedTicketList = guardService.getHistoryFinished(employee_id);
		}

		params.clear();
		params.put("ticket_id", ticket_id);
		List<Ticket> tickets = ticketDao.getTicketbyParams(params);
		String description = tickets.get(0).getDescription();

		modelAndView.addObject("state", "change");
		modelAndView.addObject("employee_id", employee_id);
		modelAndView.addObject("ticket_id",ticket_id);
		modelAndView.addObject("description", description);
		modelAndView.addObject("tbdTicketList", tbdTicketList);
		modelAndView.addObject("finishedTicketList", finishedTicketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value = "/ticket/{employee_id}", method = RequestMethod.POST)
	public ModelAndView ticketChangeResult(HttpSession session, HttpServletRequest request, @PathVariable String employee_id){

		ModelAndView modelAndView = new ModelAndView("ticket_schedule");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);
		//String dept_id = staff.get(0).getDept();

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		String dept = "guard";


		String newHandler = request.getParameter("newHandler");
		params.clear();
		params.put("staff_id", newHandler);
		List<Staff> staffs = staffDao.getStaffbyParams(params);
		String modalIcon = "fa fa-check-circle modal-icon";
		String modalTitle = "Handler Change Successful";
		String modalBody = "The ticket is now in the charge of "+newHandler+".";
		if (staffs.size()==0){
			modalIcon = "fa fa-exclamation-circle modal-icon";
			modalTitle = "Handler Change Failed";
			modalBody = "The Staff "+newHandler+" is not exist.";
		}

		List<Ticket> tbdTicketList = new ArrayList<>();
		List<Ticket> finishedTicketList = new ArrayList<>();
		if (dept == "guard"){
			tbdTicketList = guardService.tbdTicket(employee_id);
			finishedTicketList = guardService.getHistoryFinished(employee_id);
		}

		modelAndView.addObject("modalIcon",modalIcon);
		modelAndView.addObject("modalTitle",modalTitle);
		modelAndView.addObject("modalBody",modalBody);
		modelAndView.addObject("state", "post");
		modelAndView.addObject("employee_id",employee_id);
		modelAndView.addObject("tbdTicketList", tbdTicketList);
		modelAndView.addObject("finishedTicketList", finishedTicketList);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value = "/task/{employee_id}", method = RequestMethod.GET)
	public ModelAndView taskSchedule(HttpSession session, @PathVariable String employee_id){

		ModelAndView modelAndView = new ModelAndView("task_schedule");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);
		//String dept_id = staff.get(0).getDept();

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		String dept = "repairman";

		Boolean dailytask = true;
		Boolean overhaul = true;
		if (dept == "guard"||dept =="cleaner"){
			overhaul = false;
		}else{
			dailytask = false;
		}
		modelAndView.addObject("dailytask", dailytask);
		modelAndView.addObject("overhaul",overhaul);


		List<DailyTask> tbdTaskList = new ArrayList<>();
		List<DailyTask> finishedTaskList = new ArrayList<>();

		switch(dept){
			case "guard":
				tbdTaskList = guardService.tbdTask(employee_id);
				finishedTaskList = guardService.getHistoryTask(employee_id);

				tbdTaskList = finishedTaskList;

				modelAndView.addObject("tbdTaskList",tbdTaskList);
				modelAndView.addObject("finishedTaskList",finishedTaskList);
				break;
			case "cleaner":
				tbdTaskList = cleanService.tbdTask(employee_id);
				finishedTaskList = cleanService.getHistoryTask(employee_id);

				tbdTaskList = finishedTaskList;

				modelAndView.addObject("tbdTaskList",tbdTaskList);
				modelAndView.addObject("finishedTaskList",finishedTaskList);
				break;
			case "repairman":
				List<OverhaulRecord> tbdOverhaulList = overhaulService.tbdOverhaul(employee_id);
				List<OverhaulRecord> finishedOverhaulList = overhaulService.overhaulHistory(employee_id);

				tbdOverhaulList = finishedOverhaulList;

				modelAndView.addObject("tbdOverhaulList", tbdOverhaulList);
				modelAndView.addObject("finishedOverhaulList", finishedOverhaulList);
		}


		modelAndView.addObject("state", "get");
		modelAndView.addObject("employee_id",employee_id);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping("/task/{employee_id}/{task_id}")
	public ModelAndView taskChange(HttpSession session, @PathVariable String employee_id, @PathVariable String task_id){

		ModelAndView modelAndView = new ModelAndView("task_schedule");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);
		//String dept_id = staff.get(0).getDept();

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		String dept = "repairman";

		Boolean dailytask = true;
		Boolean overhaul = true;
		if (dept == "guard"||dept =="cleaner"){
			overhaul = false;
		}else{
			dailytask = false;
		}
		modelAndView.addObject("dailytask", dailytask);
		modelAndView.addObject("overhaul",overhaul);


		List<DailyTask> tbdTaskList = new ArrayList<>();
		List<DailyTask> finishedTaskList = new ArrayList<>();

		switch(dept){
			case "guard":
				tbdTaskList = guardService.tbdTask(employee_id);
				finishedTaskList = guardService.getHistoryTask(employee_id);

				tbdTaskList = finishedTaskList;

				modelAndView.addObject("tbdTaskList",tbdTaskList);
				modelAndView.addObject("finishedTaskList",finishedTaskList);
				break;
			case "cleaner":
				tbdTaskList = cleanService.tbdTask(employee_id);
				finishedTaskList = cleanService.getHistoryTask(employee_id);

				tbdTaskList = finishedTaskList;

				modelAndView.addObject("tbdTaskList",tbdTaskList);
				modelAndView.addObject("finishedTaskList",finishedTaskList);
				break;
			case "repairman":
				List<OverhaulRecord> tbdOverhaulList = overhaulService.tbdOverhaul(employee_id);
				List<OverhaulRecord> finishedOverhaulList = overhaulService.overhaulHistory(employee_id);

				tbdOverhaulList = finishedOverhaulList;

				modelAndView.addObject("tbdOverhaulList", tbdOverhaulList);
				modelAndView.addObject("finishedOverhaulList", finishedOverhaulList);
		}


		if (dailytask){
			params.clear();
			params.put("task_id", task_id);
			List<DailyTask> dailyTasks = dailyTaskDao.getTaskbyParams(params);

			String task_type = dailyTasks.get(0).getTask_type();
			String task_area = dailyTasks.get(0).getTask_area();
			modelAndView.addObject("task_type",task_type);
			modelAndView.addObject("task_area",task_area);
		}else{
			params.clear();
			params.put("overhaul_id", task_id);
			List<OverhaulRecord> overhaulRecords = overhaulRecordDao.getOverhaulbyParams(params);

			String facility_type = overhaulRecords.get(0).getFacilities().getFclt_type();
			String location = overhaulRecords.get(0).getFacilities().getLocation();
			modelAndView.addObject("facility_type",facility_type);
			modelAndView.addObject("location",location);
		}

		modelAndView.addObject("state", "change");
		modelAndView.addObject("employee_id", employee_id);
		modelAndView.addObject("task_id",task_id);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value = "/task/{employee_id}", method = RequestMethod.POST)
	public ModelAndView taskChangeResult(HttpSession session, HttpServletRequest request, @PathVariable String employee_id){

		ModelAndView modelAndView = new ModelAndView("task_schedule");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staff_name", username);
		List<Staff> staff = staffDao.getStaffbyParams(params);
		//String dept_id = staff.get(0).getDept();

		/*String staff_id = staff.get(0).getStaff_id();*/
		String staff_id = "SF1760312546";
		String dept = "repairman";


		String newHandler = request.getParameter("newHandler");
		params.clear();
		params.put("staff_id", newHandler);
		List<Staff> staffs = staffDao.getStaffbyParams(params);
		String modalIcon = "fa fa-check-circle modal-icon";
		String modalTitle = "Handler Change Successful";
		String modalBody = "The ticket is now in the charge of "+newHandler+".";
		if (staffs.size()==0){
			modalIcon = "fa fa-exclamation-circle modal-icon";
			modalTitle = "Handler Change Failed";
			modalBody = "The Staff "+newHandler+" is not exist.";
		}

		Boolean dailytask = true;
		Boolean overhaul = true;
		if (dept == "guard"||dept =="cleaner"){
			overhaul = false;
		}else{
			dailytask = false;
		}
		modelAndView.addObject("dailytask", dailytask);
		modelAndView.addObject("overhaul",overhaul);


		List<DailyTask> tbdTaskList = new ArrayList<>();
		List<DailyTask> finishedTaskList = new ArrayList<>();

		switch(dept){
			case "guard":
				tbdTaskList = guardService.tbdTask(employee_id);
				finishedTaskList = guardService.getHistoryTask(employee_id);

				tbdTaskList = finishedTaskList;

				modelAndView.addObject("tbdTaskList",tbdTaskList);
				modelAndView.addObject("finishedTaskList",finishedTaskList);
				break;
			case "cleaner":
				tbdTaskList = cleanService.tbdTask(employee_id);
				finishedTaskList = cleanService.getHistoryTask(employee_id);

				tbdTaskList = finishedTaskList;

				modelAndView.addObject("tbdTaskList",tbdTaskList);
				modelAndView.addObject("finishedTaskList",finishedTaskList);
				break;
			case "repairman":
				List<OverhaulRecord> tbdOverhaulList = overhaulService.tbdOverhaul(employee_id);
				List<OverhaulRecord> finishedOverhaulList = overhaulService.overhaulHistory(employee_id);

				tbdOverhaulList = finishedOverhaulList;

				modelAndView.addObject("tbdOverhaulList", tbdOverhaulList);
				modelAndView.addObject("finishedOverhaulList", finishedOverhaulList);
		}

		modelAndView.addObject("modalIcon",modalIcon);
		modelAndView.addObject("modalTitle",modalTitle);
		modelAndView.addObject("modalBody",modalBody);
		modelAndView.addObject("state", "post");
		modelAndView.addObject("employee_id",employee_id);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}
}

