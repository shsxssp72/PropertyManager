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
public class SupervisorController {

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
    public ModelAndView deptStaff(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("department_staff");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        String dept_id = staffDao.getSelfInfo(staff_id).getDept();

        List<Staff> staffs = supervisorService.getDeptStaff(dept_id);
        List<Staff> staffList = new ArrayList<>();
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).getPosition() != "supervisor") {
                staffList.add(staffs.get(i));
            }
        }

        modelAndView.addObject("staffList", staffList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/{employee_id}", method = RequestMethod.GET)
    public ModelAndView ticketSchedule(HttpSession session, @PathVariable String employee_id) {

        ModelAndView modelAndView = new ModelAndView("ticket_schedule");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();
        String dept_id = staffDao.getSelfInfo(staff_id).getDept();

        List<Ticket> tbdTicketList = ticketDao.tbdTicket(employee_id);
        List<Ticket> finishedTicketList = ticketDao.getHistoryFinished(employee_id);


        modelAndView.addObject("state", "get");
        modelAndView.addObject("employee_id", employee_id);
        modelAndView.addObject("tbdTicketList", tbdTicketList);
        modelAndView.addObject("finishedTicketList", finishedTicketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping("/ticket/{employee_id}/{ticket_id}")
    public ModelAndView ticketChange(HttpSession session, @PathVariable String employee_id, @PathVariable String ticket_id) {

        ModelAndView modelAndView = new ModelAndView("ticket_schedule");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();
        String dept_id = staffDao.getSelfInfo(staff_id).getDept();


        List<Ticket> tbdTicketList = ticketDao.tbdTicket(employee_id);
        List<Ticket> finishedTicketList = ticketDao.getHistoryFinished(employee_id);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ticket_id", ticket_id);
        List<Ticket> tickets = ticketDao.getTicketbyParams(params);
        String description = tickets.get(0).getDescription();

        modelAndView.addObject("state", "change");
        modelAndView.addObject("employee_id", employee_id);
        modelAndView.addObject("ticket_id", ticket_id);
        modelAndView.addObject("description", description);
        modelAndView.addObject("tbdTicketList", tbdTicketList);
        modelAndView.addObject("finishedTicketList", finishedTicketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/{employee_id}", method = RequestMethod.POST)
    public ModelAndView ticketChangeResult(HttpSession session, HttpServletRequest request, @PathVariable String employee_id) {

        ModelAndView modelAndView = new ModelAndView("ticket_schedule");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();
        String dept_id = staffDao.getSelfInfo(staff_id).getDept();


        String newHandler = request.getParameter("newHandler");
        String ticketId = request.getParameter("ticketId");
        Map<String, Object> params = new HashMap<>();
        params.put("staff_id", newHandler);
        List<Staff> staffs = staffDao.getStaffbyParams(params);
        String modalIcon = "fa fa-check-circle modal-icon";
        String modalTitle = "处理者更换成功";
        String modalBody = "现在该工单由" + newHandler + "负责。";
        if (staffs.size() == 0) {
            modalIcon = "fa fa-exclamation-circle modal-icon";
            modalTitle = "处理者更换失败";
            modalBody = "工作人员" + newHandler + "不存在。";
        }else{
            ticketDao.changeTicketHandler(newHandler, ticketId);
        }

        List<Ticket> tbdTicketList = ticketDao.tbdTicket(employee_id);
        List<Ticket> finishedTicketList = ticketDao.getHistoryFinished(employee_id);

        modelAndView.addObject("modalIcon", modalIcon);
        modelAndView.addObject("modalTitle", modalTitle);
        modelAndView.addObject("modalBody", modalBody);
        modelAndView.addObject("state", "post");
        modelAndView.addObject("employee_id", employee_id);
        modelAndView.addObject("tbdTicketList", tbdTicketList);
        modelAndView.addObject("finishedTicketList", finishedTicketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/task/{employee_id}", method = RequestMethod.GET)
    public ModelAndView taskSchedule(HttpSession session, @PathVariable String employee_id) {

        ModelAndView modelAndView = new ModelAndView("task_schedule");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();
        String dept_id = staffDao.getSelfInfo(staff_id).getDept();

        Staff employeeInfo = staffDao.getSelfInfo(employee_id);
        String dept = employeeInfo.getPosition();

        Boolean dailytask = true;
        Boolean overhaul = true;
        if (dept.equals("guard") || dept.equals("cleaner")) {
            overhaul = false;
        } else {
            dailytask = false;
        }
        modelAndView.addObject("dailytask", dailytask);
        modelAndView.addObject("overhaul", overhaul);


        List<DailyTask> tbdTaskList = new ArrayList<>();
        List<DailyTask> finishedTaskList = new ArrayList<>();

        switch (dept) {
            case "guard":
                tbdTaskList = guardService.tbdTask(employee_id);
                finishedTaskList = guardService.getHistoryTask(employee_id);

                tbdTaskList = finishedTaskList;

                modelAndView.addObject("tbdTaskList", tbdTaskList);
                modelAndView.addObject("finishedTaskList", finishedTaskList);
                break;
            case "cleaner":
                tbdTaskList = cleanService.tbdTask(employee_id);
                finishedTaskList = cleanService.getHistoryTask(employee_id);

                tbdTaskList = finishedTaskList;

                modelAndView.addObject("tbdTaskList", tbdTaskList);
                modelAndView.addObject("finishedTaskList", finishedTaskList);
                break;
            case "repairman":
                List<OverhaulRecord> tbdOverhaulList = overhaulService.tbdOverhaul(employee_id);
                List<OverhaulRecord> finishedOverhaulList = overhaulService.overhaulHistory(employee_id);

                tbdOverhaulList = finishedOverhaulList;

                modelAndView.addObject("tbdOverhaulList", tbdOverhaulList);
                modelAndView.addObject("finishedOverhaulList", finishedOverhaulList);
        }


        modelAndView.addObject("state", "get");
        modelAndView.addObject("employee_id", employee_id);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping("/task/{employee_id}/{task_id}")
    public ModelAndView taskChange(HttpSession session, @PathVariable String employee_id, @PathVariable String task_id) {

        ModelAndView modelAndView = new ModelAndView("task_schedule");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        Map<String, Object> params = new HashMap<>();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();
        String dept_id = staffDao.getSelfInfo(staff_id).getDept();

        Staff employeeInfo = staffDao.getSelfInfo(employee_id);
        String dept = employeeInfo.getPosition();

        Boolean dailytask = true;
        Boolean overhaul = true;
        if (dept.equals("guard") || dept.equals("cleaner")) {
            overhaul = false;
        } else {
            dailytask = false;
        }
        modelAndView.addObject("dailytask", dailytask);
        modelAndView.addObject("overhaul", overhaul);


        List<DailyTask> tbdTaskList = new ArrayList<>();
        List<DailyTask> finishedTaskList = new ArrayList<>();

        switch (dept) {
            case "guard":
                tbdTaskList = guardService.tbdTask(employee_id);
                finishedTaskList = guardService.getHistoryTask(employee_id);

                tbdTaskList = finishedTaskList;

                modelAndView.addObject("tbdTaskList", tbdTaskList);
                modelAndView.addObject("finishedTaskList", finishedTaskList);
                break;
            case "cleaner":
                tbdTaskList = cleanService.tbdTask(employee_id);
                finishedTaskList = cleanService.getHistoryTask(employee_id);

                tbdTaskList = finishedTaskList;

                modelAndView.addObject("tbdTaskList", tbdTaskList);
                modelAndView.addObject("finishedTaskList", finishedTaskList);
                break;
            case "repairman":
                List<OverhaulRecord> tbdOverhaulList = overhaulService.tbdOverhaul(employee_id);
                List<OverhaulRecord> finishedOverhaulList = overhaulService.overhaulHistory(employee_id);

                tbdOverhaulList = finishedOverhaulList;

                modelAndView.addObject("tbdOverhaulList", tbdOverhaulList);
                modelAndView.addObject("finishedOverhaulList", finishedOverhaulList);
        }


        if (dailytask) {
            params.clear();
            params.put("task_id", task_id);
            List<DailyTask> dailyTasks = dailyTaskDao.getTaskbyParams(params);

            String task_type = dailyTasks.get(0).getTask_type();
            String task_area = dailyTasks.get(0).getTask_area();
            modelAndView.addObject("task_type", task_type);
            modelAndView.addObject("task_area", task_area);
        } else {
            params.clear();
            params.put("overhaul_id", task_id);
            List<OverhaulRecord> overhaulRecords = overhaulRecordDao.getOverhaulbyParams(params);

            String facility_type = overhaulRecords.get(0).getFacilities().getFclt_type();
            String location = overhaulRecords.get(0).getFacilities().getLocation();
            modelAndView.addObject("facility_type", facility_type);
            modelAndView.addObject("location", location);
        }

        modelAndView.addObject("state", "change");
        modelAndView.addObject("employee_id", employee_id);
        modelAndView.addObject("task_id", task_id);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/task/{employee_id}", method = RequestMethod.POST)
    public ModelAndView taskChangeResult(HttpSession session, HttpServletRequest request, @PathVariable String employee_id) {

        ModelAndView modelAndView = new ModelAndView("task_schedule");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();
        String dept_id = staffDao.getSelfInfo(staff_id).getDept();

        Staff employeeInfo = staffDao.getSelfInfo(employee_id);
        String dept = employeeInfo.getPosition();

        Boolean dailytask = true;
        Boolean overhaul = true;
        if (dept.equals("guard")|| dept.equals("cleaner")) {
            overhaul = false;
        } else {
            dailytask = false;
        }
        modelAndView.addObject("dailytask", dailytask);
        modelAndView.addObject("overhaul", overhaul);

        String newHandler = request.getParameter("newHandler");
        String taskId = request.getParameter("taskId");
        Map<String, Object> params = new HashMap<>();
        params.put("staff_id", newHandler);
        List<Staff> staffs = staffDao.getStaffbyParams(params);
        String modalIcon = "fa fa-check-circle modal-icon";
        String modalTitle = "处理者更换成功";
        String modalBody = "现在该工单由" + newHandler + "负责。";
        if (staffs.size() == 0) {
            modalIcon = "fa fa-exclamation-circle modal-icon";
            modalTitle = "处理者更换失败";
            modalBody = "工作人员" + newHandler + "不存在。";
        }else{
            if (dailytask){
                dailyTaskDao.changeTaskHandler(newHandler, taskId);
            }else{
                overhaulRecordDao.changeOverhaulHandler(newHandler, taskId);
            }
        }



        List<DailyTask> tbdTaskList = new ArrayList<>();
        List<DailyTask> finishedTaskList = new ArrayList<>();

        switch (dept) {
            case "guard":
                tbdTaskList = guardService.tbdTask(employee_id);
                finishedTaskList = guardService.getHistoryTask(employee_id);

                tbdTaskList = finishedTaskList;

                modelAndView.addObject("tbdTaskList", tbdTaskList);
                modelAndView.addObject("finishedTaskList", finishedTaskList);
                break;
            case "cleaner":
                tbdTaskList = cleanService.tbdTask(employee_id);
                finishedTaskList = cleanService.getHistoryTask(employee_id);

                tbdTaskList = finishedTaskList;

                modelAndView.addObject("tbdTaskList", tbdTaskList);
                modelAndView.addObject("finishedTaskList", finishedTaskList);
                break;
            case "repairman":
                List<OverhaulRecord> tbdOverhaulList = overhaulService.tbdOverhaul(employee_id);
                List<OverhaulRecord> finishedOverhaulList = overhaulService.overhaulHistory(employee_id);

                tbdOverhaulList = finishedOverhaulList;

                modelAndView.addObject("tbdOverhaulList", tbdOverhaulList);
                modelAndView.addObject("finishedOverhaulList", finishedOverhaulList);
        }

        modelAndView.addObject("modalIcon", modalIcon);
        modelAndView.addObject("modalTitle", modalTitle);
        modelAndView.addObject("modalBody", modalBody);
        modelAndView.addObject("state", "post");
        modelAndView.addObject("employee_id", employee_id);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }
}

