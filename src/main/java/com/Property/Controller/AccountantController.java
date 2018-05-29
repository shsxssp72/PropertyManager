package com.Property.Controller;

import com.Property.Dao.CarIORecordDao;
import com.Property.Dao.StaffDao;
import com.Property.Domain.CarIORecord;
import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Proprietor;
import com.Property.Domain.Staff;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accountant")
public class AccountantController {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private AccountantService accountantService;
    @Autowired
    private CarIORecordDao carIORecordDao;

    @RequestMapping("/chargingSituation")
    public ModelAndView chargingSituation(HttpSession session){

        ModelAndView modelAndView = new ModelAndView("proprietor_payment_history");
        String username=(String)session.getAttribute("username");
        String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("staff_name", username);
        List<Staff> staff = staffDao.getStaffbyParams(params);

        /*String staff_id = staff.get(0).getStaff_id();*/
        String staff_id = "SF1707111159";

        List<ChargingSituation> chargingSituationList = accountantService.getAllCharging();

        modelAndView.addObject("chargingSituationList", chargingSituationList);
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

        /*String staff_id = staff.get(0).getStaff_id();*/
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
