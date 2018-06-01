package com.Property.Controller;

import com.Property.Dao.CarIORecordDao;
import com.Property.Dao.ChargingItemDao;
import com.Property.Dao.ChargingSituationDao;
import com.Property.Dao.StaffDao;
import com.Property.Domain.*;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.AccountantService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequiresRoles("accountant")
@RequestMapping("/accountant")
public class AccountantController {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private ChargingItemDao chargingItemDao;
    @Autowired
    private ChargingSituationDao chargingSituationDao;
    @Autowired
    private AccountantService accountantService;
    @Autowired
    private CarIORecordDao carIORecordDao;

    @RequestMapping("/chargingSituation")
    public ModelAndView chargingSituation(HttpSession session){

        ModelAndView modelAndView = new ModelAndView("chargingsituation_classify");
        String username=(String)session.getAttribute("username");
        String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("staff_name", username);
        List<Staff> staff = staffDao.getStaffbyParams(params);

        /*String staff_id = staff.get(0).getStaff_id();*/
        String staff_id = "SF1707111159";

        List<ChargingItem> chargingItemList = chargingItemDao.getAll();

        String item_id = chargingItemList.get(0).getItem_id();
        String item_title = chargingItemList.get(0).getItem_title();
        params.clear();
        params.put("item_title", item_title);
        List<ChargingSituation> chargingSituationList = chargingSituationDao.getPaymentbyParams(params);

        List<List<ChargingSituation>> chargingSituationLists = new ArrayList<>();

        /*for (int i= 0; i<chargingItemList.size(); i++){
            params.clear();
            params.put("item_id", chargingItemList.get(0).getItem_id());
            List<ChargingSituation> chargingSituationList = chargingSituationDao.getPaymentbyParams(params);
            chargingSituationLists.add(chargingSituationList);
        }*/

        modelAndView.addObject("chargingItemList", chargingItemList);
        modelAndView.addObject("chargingSituationList", chargingSituationList);
        modelAndView.addObject("item_title", item_title);
        modelAndView.addObject("username",username);
        modelAndView.addObject("roleName",roleName);
        return modelAndView;
    }

    @RequestMapping("/chargingSituation/{item_title}")
    public ModelAndView chargingSituationClassify(HttpSession session, @PathVariable String item_title){
        ModelAndView modelAndView = new ModelAndView("chargingsituation_classify");
        String username=(String)session.getAttribute("username");
        String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("staff_name", username);
        List<Staff> staff = staffDao.getStaffbyParams(params);

        /*String staff_id = staff.get(0).getStaff_id();*/
        String staff_id = "SF1707111159";

        List<ChargingItem> chargingItemList = chargingItemDao.getAll();

        params.clear();
        params.put("item_title", item_title);
        List<ChargingSituation> chargingSituationList = chargingSituationDao.getPaymentbyParams(params);
        /*String item_title = chargingSituationList.get(0).getFee().getChargingItem().getItem_title();*/

        modelAndView.addObject("chargingItemList", chargingItemList);
        modelAndView.addObject("chargingSituationList",chargingSituationList);
        modelAndView.addObject("item_title",item_title);
        modelAndView.addObject("username",username);
        modelAndView.addObject("roleName",roleName);
        return modelAndView;
    }

    @RequestMapping("/carCharging")
    public ModelAndView carCharging(HttpSession session){

        ModelAndView modelAndView = new ModelAndView("carIO_records");
        String username=(String)session.getAttribute("username");
        String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("staff_name", username);
        List<Staff> staff = staffDao.getStaffbyParams(params);

        //String staff_id = staff.get(0).getStaff_id();
        String staff_id = "SF1707111159";

        List<CarIORecord> carIORecords = accountantService.getExternal();
        for (int i =0; i<carIORecords.size();i++){
            System.out.println(carIORecords.get(i).getPlate_number());
        }

        modelAndView.addObject("carIORecords", carIORecords);
        modelAndView.addObject("username",username);
        modelAndView.addObject("roleName",roleName);
        return modelAndView;
    }

    @RequestMapping("/selfInfo")
    public ModelAndView selfInfo(HttpServletRequest request, HttpServletResponse response){
        //查看个人信息
        return new ModelAndView();
    }
}
