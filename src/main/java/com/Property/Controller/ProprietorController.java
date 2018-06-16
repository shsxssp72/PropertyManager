package com.Property.Controller;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.ProprietorService;
import com.Property.Utility.AssignAlgorithmUtil;
import com.Property.Utility.CryptoUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiresRoles("proprietor")
@RequestMapping("/proprietor")
public class ProprietorController {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private ProprietorService proprietorService;
    @Autowired
    private ProprietorDao proprietorDao;
    @Autowired
    private ChargingSituationDao chargingSituationDao;
    @Autowired
    private SubareaDao subareaDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FeeDao feeDao;
    @Autowired
    private SuggestionDao suggestionDao;

    private CryptoUtil cryptoUtil;

    @ApiOperation(value = "业主待缴费项目条目")
    @RequestMapping("/payment/pay")
    public ModelAndView payment(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("proprietor_payment_pay");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());
        List<ChargingSituation> chargingSituationList = proprietorService.getPayment(prprt_id);

        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("chargingSituationList", chargingSituationList);
        return modelAndView;
    }

    @ApiOperation(value = "业主对对应待缴费项进行缴费")
    @RequestMapping("/invoice/{situation_id}")
    public ModelAndView invoice(HttpSession session, @PathVariable String situation_id) {
        ModelAndView modelAndView = new ModelAndView("proprietor_invoice");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        Proprietor proprietorInfo = proprietorDao.getSelfInfo(userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid()));
        String tel = proprietorInfo.getTel();

        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("situation_id", situation_id);
        List<ChargingSituation> chargingSituationList = proprietorService.getPaymentbyParams(params1);
        ChargingSituation chargingSituation = new ChargingSituation();
        if (chargingSituationList!=null)
            chargingSituation = chargingSituationList.get(0);

        Date now = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        String date = myFmt.format(now);

        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("tel", tel);
        modelAndView.addObject("chargingSituation", chargingSituation);
        modelAndView.addObject("date", date);

        return modelAndView;
    }

    @ApiOperation(value = "业主已缴费项目条目")
    @RequestMapping("/payment/history")
    public ModelAndView history(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("proprietor_payment_history");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());
        List<ChargingSituation> chargingSituationList = proprietorService.getPaymentHistory(prprt_id);

        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("chargingSituationList", chargingSituationList);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/new", method = RequestMethod.GET)
    public ModelAndView callRepair(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("ticket_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
        String realName = proprietorDao.getSelfInfo(userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid())).getPrprt_name();

        modelAndView.addObject("state", "get");
        modelAndView.addObject("realName", realName);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/new", method = RequestMethod.POST)
    public ModelAndView callRepairResult(HttpSession session, HttpServletRequest request) {

        session.setAttribute("state", "post");
        ModelAndView modelAndView = new ModelAndView("ticket_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());

        String ticketType = request.getParameter("ticketType");
        String proprietor = proprietorDao.getSelfInfo(userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid())).getPrprt_name();
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
			ticket.setInitiator_prprt_id(prprt_id);
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

			proprietorService.prprtCallRepair(ticket);

            String cs_id="CS"+cryptoUtil.getRandomNumber(15);                                           //插入新缴费项
            for(;chargingSituationDao.getIdCount(cs_id)!=0;)
                cs_id="CS"+cryptoUtil.getRandomNumber(15);
            ChargingSituation chargingSituation=new ChargingSituation();
            chargingSituation.setSituation_id(cs_id);
            chargingSituation.setFee_id(feeDao.getTicketItem());
            chargingSituation.setPrprt_id(prprt_id);
            chargingSituation.setCollector_id("SF0000000000");
            proprietorService.generateTicketFee(chargingSituation);
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
        modelAndView.addObject("realName", proprietor);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/ticket/history", method = RequestMethod.GET)
    public ModelAndView repairHistory(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("ticket_history");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());

        List<Ticket> ticketList = proprietorService.getRepairHistory(prprt_id);
        List<Ticket> unfinishedList = new ArrayList<Ticket>();
        List<Ticket> finishedList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            if (ticket.getInitiator_prprt_id() != null) {
                if (ticket.getTicket_result() == null) {
                    unfinishedList.add(ticket);
                } else {
                    finishedList.add(ticket);
                }
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
    public ModelAndView repairFeedback(HttpSession session, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("ticket_history");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());

        String ticketId = request.getParameter("ticketId");
        String ticketFdbk = request.getParameter("ticketFdbk");

        proprietorService.updateRepairFB(Integer.parseInt(ticketFdbk), prprt_id, ticketId);
        System.out.println(ticketId + " " + ticketFdbk);

        List<Ticket> ticketList = proprietorService.getRepairHistory(prprt_id);
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

    @RequestMapping(value = "/suggestion/new", method = RequestMethod.GET)
    public ModelAndView giveAdvice(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("suggestion_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());

        modelAndView.addObject("state", "get");
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/suggestion/new", method = RequestMethod.POST)
    public ModelAndView sendAdvice(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("suggestion_new");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());

        String anonymous[] = request.getParameterValues("anonymous");
        String name;
        if (anonymous != null) {
            name = "anonymous";
        } else {
            name = proprietorService.getSelfInfo(prprt_id).getPrprt_name();
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
            suggestion.setPrprt_id(prprt_id);
        }
        suggestion.setSuggestion_type(suggestionType);
        suggestion.setSuggestion_detail(suggestionDetail);

        //提出意见
        proprietorService.giveAdvice(suggestion);
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

        String prprt_id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());

        List<Suggestion> suggestions = proprietorService.getAdviceHistory(prprt_id);

        modelAndView.addObject("suggestions", suggestions);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping("/selfInfo")
    public ModelAndView selfInfo(HttpServletRequest request, HttpServletResponse response) {
        //个人信息
        return new ModelAndView();
    }

    @RequestMapping("/alterTel")
    public ModelAndView alterTel(HttpServletRequest request, HttpServletResponse response) {
        //更改联系电话
        return new ModelAndView();
    }
}
