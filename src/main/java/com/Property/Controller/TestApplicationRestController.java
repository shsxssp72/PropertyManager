package com.Property.Controller;

import com.Property.Dao.ChargingSituationDao;
import com.Property.Dao.ProprietorDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.*;
import com.Property.Entity.RolePermission;
import com.Property.Entity.UserInfo;
import com.Property.Mapper.RolePermissionMapper;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.*;
import com.Property.Utility.CryptoUtil;
import com.Property.Utility.Pair;
import com.Property.Utility.TableAttrGetter;
import com.Property.Utility.WeatherRequest;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

@RestController
//@Api(value="/Test",tags="测试用页面")
public class TestApplicationRestController {

    @Autowired
    ProprietorDao proprietorDao;

    @Autowired
    StaffDao staffDao;

    @Autowired
    UserInfoService userInfoService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView toLogin(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("login");
        CryptoUtil cryptoUtil = new CryptoUtil();
        Pair<RSAPrivateKey, RSAPublicKey> keyPair = cryptoUtil.generateRSAKey();
//		session.setAttribute("LoginPublicKey",cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));
        session.setAttribute("LoginPublicKey", org.apache.commons.codec.binary.Base64
                .encodeBase64String(keyPair.getValue().getEncoded()));
        session.setAttribute("LoginPublicKeyOriginal", keyPair.getValue());
        session.setAttribute("LoginPrivateKey", keyPair.getKey());


//		session.setAttribute("enc_data",cryptoUtil.RSAEncrypt(keyPair.getValue(),"aeiuo"));


//		System.out.println("PKCS1: "+cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));


        modelAndView.addObject("title", "Login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView LoginVerify(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("login");
        System.out.println("From LoginVerify: Login Verify Started.");
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            String errorMessage;
            if (UnknownAccountException.class.getName().equals(exception)) {
                errorMessage = "Non-existent Username.";
                System.out.println("From LoginVerify: " + errorMessage);
                msg = "Invalid username or password ";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                errorMessage = "Invalid Password.";
                System.out.println("From LoginVerify: " + errorMessage);
                msg = "Invalid username or password ";
            } else {
                msg = "Inner Error.";
                errorMessage = "Unexpected Error: " + exception;
                System.out.println("From LoginVerify: " + errorMessage);
            }
        }
        modelAndView.addObject("msg", msg);
        System.out.println("From LoginVerify: Login Verify Ended.");
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpSession session) {

        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Empty();
    }

    @RequestMapping("/empty")
    public ModelAndView Empty() {
        return new ModelAndView("empty");
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @RequiresUser
    public ModelAndView getProfile(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("profile");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
        Staff staffInfo;
        Proprietor proprietorInfo;
        if (roleName.equals("proprietor")) {
            proprietorInfo = proprietorDao
                    .getSelfInfo(userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid()));
            modelAndView.addObject("ID", proprietorInfo.getPrprt_id());
            modelAndView.addObject("Name", proprietorInfo.getPrprt_name());
            modelAndView.addObject("Gender", proprietorInfo.getGender());
            modelAndView.addObject("Tel", proprietorInfo.getTel());
            modelAndView.addObject("Birthday", proprietorInfo.getBirthday());
            modelAndView.addObject("Apartment", "Flat " + proprietorInfo.getAprt_building() +
                    ", Floor " + proprietorInfo.getAprt_floor() + ", Room " + proprietorInfo.getAprt_room_num());
        } else {
            staffInfo = staffDao
                    .getSelfInfo(userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid()));
            modelAndView.addObject("ID", staffInfo.getStaff_id());
            modelAndView.addObject("Name", staffInfo.getStaff_name());
            modelAndView.addObject("Gender", staffInfo.getGender());
            modelAndView.addObject("Tel", staffInfo.getTel());
            modelAndView.addObject("Address", staffInfo.getAddress());
            modelAndView.addObject("Department", staffInfo.getDept());
            modelAndView.addObject("Position", staffInfo.getPosition());
        }
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @RequiresUser
    public ModelAndView toChangePassword(HttpServletRequest request) {
        return getChangePasswd(request);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    @RequiresUser
    public ModelAndView getChangePasswd(HttpServletRequest request) {
        CryptoUtil cryptoUtil = new CryptoUtil();
        Pair<RSAPrivateKey, RSAPublicKey> keyPair = cryptoUtil.generateRSAKey();
//		session.setAttribute("LoginPublicKey",cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));
        request.getSession().setAttribute("CP_PublicKey", org.apache.commons.codec.binary.Base64
                .encodeBase64String(keyPair.getValue().getEncoded()));
        request.getSession().setAttribute("CP_PublicKeyOriginal", keyPair.getValue());
        request.getSession().setAttribute("CP_PrivateKey", keyPair.getKey());


        System.out.println("From getChangePasswd: PUBLIC KEY: " + org.apache.commons.codec.binary.Base64
                .encodeBase64String(keyPair.getValue().getEncoded()));
        System.out.println("From getChangePasswd: PRIVATE KEY: " + org.apache.commons.codec.binary.Base64
                .encodeBase64String(keyPair.getKey().getEncoded()));


        ModelAndView modelAndView = new ModelAndView("changePassword");
        String username = (String) request.getSession().getAttribute("username");
        String Msg = "";
        modelAndView.addObject("username", username);
        modelAndView.addObject("msg", Msg);
        return modelAndView;
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @RequiresUser
    public ModelAndView postChangePasswd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        CryptoUtil cryptoUtil = new CryptoUtil();
        PrivateKey privateKey = (PrivateKey) session.getAttribute("CP_PrivateKey");
        PublicKey publicKey = (PublicKey) session.getAttribute("CP_PublicKeyOriginal");


        String username = (String) session.getAttribute("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");

        System.out.println("From postChangePasswd: INITIAL OLDPASSWORD: " + oldPassword);
        System.out.println("From postChangePasswd: INITIAL NEWPASSWORD: " + newPassword);
        System.out.println("From postChangePasswd: INITIAL NEWPASSWORDCONFIRM: " + newPasswordConfirm);


        oldPassword = cryptoUtil.RSADecrypt(privateKey, oldPassword);
        newPassword = cryptoUtil.RSADecrypt(privateKey, newPassword);
        newPasswordConfirm = cryptoUtil.RSADecrypt(privateKey, newPasswordConfirm);


        UserInfo userInfo = userInfoMapper.getByUserName(username);

        //先验证旧的密码
        String alg = "SHA-512";
        int iter = 5;
        String Msg = "";
        String verification = cryptoUtil.getHashedPassword(oldPassword, username, userInfo.getSalt());

        if (!verification.equals(userInfo.getUser_password())) {
            Msg = "Original Password Incorrect.";
            System.out.println("From postChangePasswd :" + Msg);
            System.out.println("From postChangePasswd :old:" + userInfo.getUser_password());
            System.out.println("From postChangePasswd :new:" + verification);
        } else if (!newPassword.equals(newPasswordConfirm)) {
            Msg = "New Password Not Match.";
            System.out.println("From postChangePasswd :" + Msg);
        } else {
            String newSalt = cryptoUtil.getRandomString(128);
            String newCrd = cryptoUtil.getHashedPassword(newPassword, username, newSalt);
            userInfoMapper.updatePassword(userInfoService.getUidByUserName(username), (String) newCrd, newSalt);
            Msg = "Success";
            System.out.println("From postChangePasswd :" + Msg);


            return logout(request.getSession());
        }


        return getChangePasswd(request).addObject("msg", Msg);
    }


    @Autowired
    private TicketDao ticketDao;


    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ProprietorService proprietorService;
    @Autowired
    GuardService guardService;
    @Autowired
    CleanService cleanService;
    @Autowired
    OverhaulService overhaulService;

    private String[] proprietorHints = {"保护自身安全", "注意天气变化", "邻里和谐相处"};
    private String[] staffHints = {"尽心服务业主", "工作保护自身安全", "维持小区秩序"};

    @RequestMapping(value = {"", "/", "/index"})
    public ModelAndView Index(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("index");
        String username = (String) session.getAttribute("username");
        String roleName = sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
        String id;
        if (roleName.equals("proprietor")) {
            id = userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid());
        } else {
            id = userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid());
        }


        Calendar calendar = Calendar.getInstance();
        String greeting;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 12)
            greeting = "早上好! ";
        else if (hour <= 17)
            greeting = "下午好! ";
        else
            greeting = "晚上好! ";
        String date = greeting + "今天是 " + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar
                .get(Calendar.DAY_OF_MONTH) + ".\n";

        WeatherRequest weatherRequest = new WeatherRequest();
        Map<String, Object> weather = null;
        String weatherJSON = weatherRequest.getWeather();
        weather = weatherRequest.parseJSONToWeather(weatherJSON);

        String cond = weather.get("cond_txt_d").toString();
        String cond_icon = "";
        if (cond.equals("晴")) {
            cond_icon = "fa fa-sun-o fa-3x";
        } else if (cond.indexOf("雪") >= 0) {
            cond_icon = "fa fa-asterisk fa-3x";
        } else if (cond.indexOf("雨") >= 0) {
            cond_icon = "fa fa-tint fa-3x";
        } else {
            cond_icon = "fa fa-cloud fa-3x";
        }

        String tmp_min = weather.get("tmp_min").toString();
        String tmp_max = weather.get("tmp_max").toString();
        String pop = weather.get("pop").toString();
        String hum = weather.get("hum").toString();
        String wind_dir = weather.get("wind_dir").toString();
        String weatherData = "最低温：" + tmp_min + "°C  最高温：" + tmp_max + "°C  降水概率：" + pop + "%  相对湿度：" + hum + "%  风向：" + wind_dir;

        Map<String, Object> lifestyle = weatherRequest.parseJSONToLifeStyle(weatherRequest.getLifeStyle());
        String comf = "舒适度指数：" + lifestyle.get("comf").toString();
        String drsg = "穿衣指数：" + lifestyle.get("drsg").toString();
        String flu = "感冒指数：" + lifestyle.get("flu").toString();
        String sport = "运动指数：" + lifestyle.get("sport").toString();
        String air = "空气指数：" + lifestyle.get("air").toString();

        Boolean moreAlerts = false;
        if (roleName.equals("proprietor")) {
            List<ChargingSituation> chargingSituationList = proprietorService.getPaymentHistory(id);
            if (chargingSituationList.size() > 3) {
                moreAlerts = true;
            } else {
                moreAlerts = false;
            }

            int i = 0;
            for (; i < chargingSituationList.size(); i++) {
                String name = "alert" + i;
                modelAndView.addObject(name, chargingSituationList.get(i).getFee().getChargingItem().getItem_title());
                if (i == 3) {
                    break;
                }
            }
            if (i < 3) {
                while (i <= 2) {
                    String name = "alert" + i;
                    modelAndView.addObject(name, proprietorHints[i]);
                    i++;
                }
            }
            modelAndView.addObject("moreAlerts", moreAlerts);
        } else if (roleName.equals("guard") || roleName.equals("cleaner")) {
            List<DailyTask> dailyTaskList;
            List<Ticket> ticketList;
            if (roleName.equals("guard")) {
                dailyTaskList = guardService.tbdTask(id);
                ticketList = guardService.tbdTicket(id);
            } else {
                dailyTaskList = cleanService.tbdTask(id);
                ticketList = cleanService.tbdTicket(id);
            }

            if ((dailyTaskList.size() + ticketList.size()) > 3) {
                moreAlerts = true;
            }

            List<String> TypeList = new ArrayList<>();
            if ((dailyTaskList.size() + ticketList.size()) > 0) {
                if (dailyTaskList.size() > 0) {
                    TypeList.add(dailyTaskList.get(0).getTask_type());
                }
                if (dailyTaskList.size() > 1) {
                    TypeList.add(dailyTaskList.get(1).getTask_type());
                }
                if (ticketList.size() > 0) {
                    TypeList.add(ticketList.get(0).getTicket_type());
                }
                if (ticketList.size() > 1) {
                    TypeList.add(ticketList.get(1).getTicket_type());
                }
            }

            int i = 0;
            for (; i < TypeList.size(); i++) {
                String name = "alert" + i;
                modelAndView.addObject(name, TypeList.get(i));
            }
            if (i < 3) {
                while (i <= 2) {
                    String name = "alert" + i;
                    modelAndView.addObject(name, staffHints[i]);
                    i++;
                }
            }
            modelAndView.addObject("moreAlerts", moreAlerts);
        } else if (roleName.equals("repairman")) {
            List<OverhaulRecord> overhaulList = overhaulService.tbdOverhaul(id);
            List<Ticket> ticketList = overhaulService.tbdTicket(id);


            if ((overhaulList.size() + ticketList.size()) > 3) {
                moreAlerts = true;
            }

            List<String> TypeList = new ArrayList<>();
            if ((overhaulList.size() + ticketList.size()) > 0) {
                if (overhaulList.size() > 0) {
                    TypeList.add(overhaulList.get(0).getOverhaul_type());
                }
                if (overhaulList.size() > 1) {
                    TypeList.add(overhaulList.get(1).getOverhaul_type());
                }
                if (ticketList.size() > 0) {
                    TypeList.add(ticketList.get(0).getTicket_type());
                }
                if (ticketList.size() > 1) {
                    TypeList.add(ticketList.get(1).getTicket_type());
                }
            }

            int i = 0;
            for (; i < TypeList.size(); i++) {
                String name = "alert" + i;
                modelAndView.addObject(name, TypeList.get(i));
            }
            if (i < 3) {
                while (i <= 2) {
                    String name = "alert" + i;
                    modelAndView.addObject(name, staffHints[i]);
                    i++;
                }
            }
            modelAndView.addObject("moreAlerts", moreAlerts);
        } else {
            int j=0;
            while(j<=2){
                String name = "alert"+j;
                modelAndView.addObject(name, staffHints[j]);
                j++;
            }
            modelAndView.addObject("moreAlerts", false);
        }

        modelAndView.addObject("cond_icon", cond_icon);
        modelAndView.addObject("weatherData", weatherData);
        modelAndView.addObject("cond_txt_d", weather.get("cond_txt_d").toString());
        modelAndView.addObject("comf", comf);
        modelAndView.addObject("drsg", drsg);
        modelAndView.addObject("flu", flu);
        modelAndView.addObject("sport", sport);
        modelAndView.addObject("air", air);
        modelAndView.addObject("username", username);
        modelAndView.addObject("roleName", roleName);
        modelAndView.addObject("date", date);

        return modelAndView;
    }
}
