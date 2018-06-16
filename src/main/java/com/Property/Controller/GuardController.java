package com.Property.Controller;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.GuardService;
import com.Property.Utility.AssignAlgorithmUtil;
import com.Property.Utility.CryptoUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.eclipse.jetty.util.DateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiresRoles("guard")
@RequestMapping("/guard")
public class GuardController {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private SubareaDao subareaDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private SuggestionDao suggestionDao;
    @Autowired
    private GuardService guardService;
    @Autowired
    private DailyTaskDao dailyTaskDao;

    @RequestMapping(value = "/dailytask/tbd", method = RequestMethod.GET)
    public ModelAndView tbdTask(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("dailytask_tbd");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<DailyTask> dailyTaskList = guardService.tbdTask(staff_id);

        modelAndView.addObject("dailyTaskList", dailyTaskList);
        modelAndView.addObject("state", "get");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping("/task/finish/{task_id}")
    public ModelAndView finishTask(HttpSession session, @PathVariable String task_id) {
        ModelAndView modelAndView = new ModelAndView("dailytask_tbd");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("task_id", task_id);
        List<DailyTask> dailyTasks = dailyTaskDao.getTaskbyParams(params);

        modelAndView.addObject("task_id", task_id);
        modelAndView.addObject("task_type", dailyTasks.get(0).getTask_type());
        modelAndView.addObject("task_area", dailyTasks.get(0).getTask_area());
        modelAndView.addObject("state", "finish");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping(value = "/dailytask/tbd", method = RequestMethod.POST)
    public ModelAndView finishTaskResult(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("dailytask_tbd");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<DailyTask> dailyTaskList = guardService.tbdTask(staff_id);

        String taskID = request.getParameter("taskId");
        Boolean isException;
        if (request.getParameter("isException") == null) {
            isException = false;
        } else {
            isException = true;
        }
        String taskResult = request.getParameter("taskResult");
        //System.out.println(taskID + " " + isException + " " + taskResult);
        //完成任务结果信息
        guardService.finishTask(taskResult, isException, taskID);

        modelAndView.addObject("dailyTaskList", dailyTaskList);
        modelAndView.addObject("state", "post");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping("/dailytask/finished")
    public ModelAndView historyTask(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("dailytask_finished");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<DailyTask> dailyTaskList = guardService.getHistoryTask(staff_id);

        modelAndView.addObject("dailyTaskList", dailyTaskList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping("/carIO")
    public ModelAndView carIO(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("carIO_records");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<CarIORecord> carIORecords = guardService.getAll();

        modelAndView.addObject("carIORecords", carIORecords);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping("/externalCar")
    public ModelAndView externalCar(HttpServletRequest request, HttpServletResponse response) {
        //查看外来车辆记录
        return new ModelAndView();
    }

    @RequestMapping(value = "/ticket/tbd", method = RequestMethod.GET)
    public ModelAndView tbdTicket(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("ticket_tbd");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<Ticket> ticketList = guardService.tbdTicket(staff_id);

        modelAndView.addObject("state", "get");
        modelAndView.addObject("ticketList", ticketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping("/ticket/finish/{ticket_id}")
    public ModelAndView finishingTicket(HttpSession session, @PathVariable String ticket_id) {
        ModelAndView modelAndView = new ModelAndView("ticket_tbd");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<Ticket> ticketList = guardService.tbdTicket(staff_id);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ticket_id", ticket_id);
        List<Ticket> tickets = ticketDao.getTicketbyParams(params);

        modelAndView.addObject("ticket_id", ticket_id);
        modelAndView.addObject("description", tickets.get(0).getDescription());
        modelAndView.addObject("state", "finish");
        modelAndView.addObject("ticketList", ticketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/tbd", method = RequestMethod.POST)
    public ModelAndView finishTicket(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("ticket_tbd");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        String ticketId = request.getParameter("ticketId");
        String ticketResult = request.getParameter("ticketResult");
        //进行ticket结果更新
        guardService.finishTicket(new Timestamp(new Date().getTime()), ticketResult, ticketId);
        System.out.println(ticketId + " " + ticketResult);

        List<Ticket> ticketList = guardService.tbdTicket(staff_id);

        modelAndView.addObject("state", "post");
        modelAndView.addObject("ticketList", ticketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping("/ticket/finished")
    public ModelAndView finishHistory(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("ticket_finished");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<Ticket> ticketList = guardService.getHistoryFinished(staff_id);

        modelAndView.addObject("ticketList", ticketList);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/new", method = RequestMethod.GET)
    public ModelAndView createTicket(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("ticket_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        modelAndView.addObject("state", "get");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/new", method = RequestMethod.POST)
    public ModelAndView createTicketResult(HttpSession session, HttpServletRequest request) {

        session.setAttribute("state", "post");
        ModelAndView modelAndView = new ModelAndView("ticket_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        String ticketType = request.getParameter("ticketType");
        String proprietor = request.getParameter("initiator");
        String subarea = request.getParameter("subarea");
        String building = request.getParameter("building");
        String floor = request.getParameter("floor");
        String room = request.getParameter("room");
        String description = request.getParameter("description");

        System.out.println(ticketType + " " + proprietor + " " + subarea + " " + building + " " + floor + " " + room + " " + description);

        Boolean validation = true;                                                          //判断输入地址信息是否合法
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("subarea_id", subarea);
        List<Subarea> subareas = subareaDao.getSubareabyParams(params);
        if (subareas.size() == 0)
            validation = false;
        else {
            params.clear();
            params.put("building_id", building);
            List<Building> buildings = buildingDao.getBuildingbyParams(params);
            if (buildings.size() == 0)
                validation = false;
            else {
                if (Integer.parseInt(floor) > buildings.get(0).getMax_floor())
                    validation = false;
                else {
                    if (Integer.parseInt(room) > buildings.get(0).getMax_room_num())
                        validation = false;
                }
            }
        }

        if (validation) {
            String[][] rev_task = new String[100][100];
            params.clear();
            int size = 0;
            switch (ticketType) {
                case "guard":
                    params.put("position", "guard");
                    List<Staff> guardList = staffDao.getStaffbyParams(params);
                    size = guardList.size();
                    for (int i = 0; i < size; i++) {
                        rev_task[i][0] = guardList.get(i).getStaff_id();
                        rev_task[i][1] = String.valueOf(ticketDao.tbdTicket(rev_task[i][0]).size());
                    }
                    break;
                case "clean":
                    params.put("position", "cleaner");
                    List<Staff> cleanerList = staffDao.getStaffbyParams(params);
                    size = cleanerList.size();
                    for (int i = 0; i < size; i++) {
                        rev_task[i][0] = cleanerList.get(i).getStaff_id();
                        rev_task[i][1] = String.valueOf(ticketDao.tbdTicket(rev_task[i][0]).size());
                    }
                    break;
                case "electricity":
                    params.put("position", "repairman");
                    params.put("elec_qlfy", true);
                    List<Staff> elecRepairmanList = staffDao.getStaffbyParams(params);
                    size = elecRepairmanList.size();
                    for (int i = 0; i < size; i++) {
                        rev_task[i][0] = elecRepairmanList.get(i).getStaff_id();
                        rev_task[i][1] = String.valueOf(ticketDao.tbdTicket(rev_task[i][0]).size());
                    }
                    break;
                case "pipe":
                    params.put("position", "repairman");
                    params.put("plbr_qlfy", true);
                    List<Staff> plbrRepairmanList = staffDao.getStaffbyParams(params);
                    size = plbrRepairmanList.size();
                    for (int i = 0; i < size; i++) {
                        rev_task[i][0] = plbrRepairmanList.get(i).getStaff_id();
                        rev_task[i][1] = String.valueOf(ticketDao.tbdTicket(rev_task[i][0]).size());
                    }
                    break;
                default:
            }

            AssignAlgorithmUtil.taskAllocation(1, size, rev_task);                                     //任务分配

            CryptoUtil cryptoUtil = new CryptoUtil();
            String t_id="T"+cryptoUtil.getRandomNumber(10);
            for(;ticketDao.getIdCount(t_id)!=0;)
                t_id="T"+cryptoUtil.getRandomNumber(10);
            Ticket ticket=new Ticket();
            ticket.setTicket_id(t_id);
            ticket.setTicket_type(ticketType);
            ticket.setTicket_time(new Timestamp(new Date().getTime()));
            ticket.setInitiator_staff_id(staff_id);
            ticket.setSubarea_id(subarea);
            ticket.setAprt_building(building);
            ticket.setAprt_floor(Integer.parseInt(floor));
            ticket.setAprt_room_num(Integer.parseInt(room));
            ticket.setDescription(description);                                                                 //插入工单项

            for (int i =0;i<size;i++){
                if (rev_task[i][2].equals("1")){
                    ticket.setHandler_id(rev_task[i][0]);
                }
            }

            guardService.staffTicket(ticket);
        }

        String modalIcon;
        String modalTitle;
        String modalContent;
        if (validation) {
            modalIcon = "fa fa-check-circle modal-icon";
            modalTitle = "发起工单成功";
            modalContent = "你已经成功地发起了一份工单，现在你可以在工单历史部分来检查你的工单状态。";
        } else {
            modalIcon = "fa fa-exclamation-circle modal-icon";
            modalTitle = "发起工单失败";
            modalContent = "未能成功发起工单，请检查你所填写的信息是否正确。";
        }


        modelAndView.addObject("state", "post");
        modelAndView.addObject("modalIcon", modalIcon);
        modelAndView.addObject("modalTitle", modalTitle);
        modelAndView.addObject("modalContent", modalContent);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName",realName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/history", method = RequestMethod.GET)
    public ModelAndView createHistory(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("ticket_history");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        List<Ticket> ticketList = guardService.getHistoryCreated(staff_id);
        List<Ticket> unfinishedList = new ArrayList<Ticket>();
        List<Ticket> finishedList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            if (ticket.getTicket_result() == null) {
                unfinishedList.add(ticket);
            } else {
                finishedList.add(ticket);
            }
        }

        modelAndView.addObject("unfinishedList", unfinishedList);
        modelAndView.addObject("finishedList", finishedList);
        modelAndView.addObject("state", "get");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/history", method = RequestMethod.POST)
    public ModelAndView createFeedback(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("ticket_history");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        String ticketId = request.getParameter("ticketId");
        String ticketFdbk = request.getParameter("ticketFdbk");

        //进行工单评价的更新
        ticketDao.updateRepairFB(Integer.parseInt(ticketFdbk), staff_id, ticketId);
        System.out.println(ticketId + " " + ticketFdbk);

        List<Ticket> ticketList = guardService.getHistoryCreated(staff_id);
        List<Ticket> unfinishedList = new ArrayList<Ticket>();
        List<Ticket> finishedList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            if (ticket.getTicket_result() == null) {
                unfinishedList.add(ticket);
            } else {
                finishedList.add(ticket);
            }
        }

        modelAndView.addObject("unfinishedList", unfinishedList);
        modelAndView.addObject("finishedList", finishedList);
        modelAndView.addObject("state", "post");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping("/selfInfo")
    public ModelAndView selfInfo(HttpServletRequest request, HttpServletResponse response) {
        //查看个人信息
        return new ModelAndView();
    }

    @RequestMapping(value = "/suggestion/new", method = RequestMethod.GET)
    public ModelAndView giveAdvice(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("suggestion_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        modelAndView.addObject("state", "get");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("realName", realName);
        return modelAndView;
    }

    @RequestMapping(value = "/suggestion/new", method = RequestMethod.POST)
    public ModelAndView sendAdvice(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("suggestion_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        String anonymous[] = request.getParameterValues("anonymous");
        String name;
        if (anonymous != null) {
            name = "anonymous";
        } else {
            name = username;
        }

        String suggestionType = request.getParameter("suggestionType");
        String suggestionDetail = request.getParameter("suggestionDetail");
        Suggestion suggestion = new Suggestion();
        CryptoUtil cryptoUtil = new CryptoUtil();
        String id="SG"+cryptoUtil.getRandomNumber(10);
        for(;suggestionDao.getIdCount(id)!=0;)
            id="SG"+cryptoUtil.getRandomNumber(10);
        suggestion.setSuggestion_id(id);
        if (!name.equals("anonymous")){
            suggestion.setPrprt_id(staff_id);
        }
        suggestion.setSuggestion_type(suggestionType);
        suggestion.setSuggestion_detail(suggestionDetail);

        //提出意见
        suggestionDao.giveAdvice(suggestion);
        //提出意见
        System.out.println(suggestionType + " " + name + " " + suggestionDetail);

        modelAndView.addObject("state", "post");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping("/suggestion/history")
    public ModelAndView adviceHistory(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("suggestion_history");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String staff_id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        String realName = staffDao.getSelfInfo(staff_id).getStaff_name();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("prprt_id", staff_id);
        List<Suggestion> suggestions = suggestionDao.getSuggestionbyParams(params);

        modelAndView.addObject("suggestions", suggestions);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }
}
