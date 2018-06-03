package com.Property.Controller;

import com.Property.Dao.ProprietorDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.Proprietor;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import com.Property.Entity.RolePermission;
import com.Property.Entity.UserInfo;
import com.Property.Mapper.RolePermissionMapper;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.UserInfoService;
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
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.eclipse.jetty.server.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

@RestController
//@Api(value="/Test",tags="测试用页面")
public class TestApplicationRestController
{

	@Autowired
	ProprietorDao proprietorDao;

	@Autowired
	StaffDao staffDao;

	@Autowired
	UserInfoService userInfoService;


	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView toLogin(HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("login");
		CryptoUtil cryptoUtil=new CryptoUtil();
		Pair<RSAPrivateKey,RSAPublicKey> keyPair=cryptoUtil.generateRSAKey();
//		session.setAttribute("LoginPublicKey",cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));
		session.setAttribute("LoginPublicKey",org.apache.commons.codec.binary.Base64
				.encodeBase64String(keyPair.getValue().getEncoded()));
		session.setAttribute("LoginPublicKeyOriginal",keyPair.getValue());
		session.setAttribute("LoginPrivateKey",keyPair.getKey());


//		session.setAttribute("enc_data",cryptoUtil.RSAEncrypt(keyPair.getValue(),"aeiuo"));


//		System.out.println("PKCS1: "+cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));


		modelAndView.addObject("title","Login");
		return modelAndView;
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView LoginVerify(HttpServletRequest request) throws Exception
	{
		ModelAndView modelAndView=new ModelAndView("login");
		System.out.println("From LoginVerify: Login Verify Started.");
		String exception=(String)request.getAttribute("shiroLoginFailure");
		System.out.println("exception="+exception);
		String msg="";
		if(exception!=null)
		{
			String errorMessage;
			if(UnknownAccountException.class.getName().equals(exception))
			{
				errorMessage="Non-existent Username.";
				System.out.println("From LoginVerify: "+errorMessage);
				msg="Invalid username or password ";
			}
			else if(IncorrectCredentialsException.class.getName().equals(exception))
			{
				errorMessage="Invalid Password.";
				System.out.println("From LoginVerify: "+errorMessage);
				msg="Invalid username or password ";
			}
			else
			{
				msg="Inner Error.";
				errorMessage="Unexpected Error: "+exception;
				System.out.println("From LoginVerify: "+errorMessage);
			}
		}
		modelAndView.addObject("msg",msg);
		System.out.println("From LoginVerify: Login Verify Ended.");
		return modelAndView;
	}

	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public ModelAndView logout(HttpSession session)
	{

		Subject currentUser=SecurityUtils.getSubject();
		currentUser.logout();
		return Empty();
	}

	@RequestMapping("/empty")
	public ModelAndView Empty()
	{
		return new ModelAndView("empty");
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	@RequiresUser
	public ModelAndView getProfile(HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("profile");
		String username=(String)session.getAttribute("username");
		String roleName=sysRoleMapper.getByUid(userInfoMapper.getByUserName(username).getUid()).getRole_name();
		Staff staffInfo;
		Proprietor proprietorInfo;
		if(roleName.equals("proprietor"))
		{
			proprietorInfo=proprietorDao
					.getSelfInfo(userInfoMapper.getPrprtIDByUid(userInfoMapper.getByUserName(username).getUid()));
			modelAndView.addObject("ID",proprietorInfo.getPrprt_id());
			modelAndView.addObject("Name",proprietorInfo.getPrprt_name());
			modelAndView.addObject("Gender",proprietorInfo.getGender());
			modelAndView.addObject("Tel",proprietorInfo.getTel());
			modelAndView.addObject("Birthday",proprietorInfo.getBirthday());
			modelAndView.addObject("Apartment","Flat "+proprietorInfo.getAprt_building()+
					", Floor "+proprietorInfo.getAprt_floor()+", Room "+proprietorInfo.getAprt_room_num());
		}
		else
		{
			staffInfo=staffDao
					.getSelfInfo(userInfoMapper.getStaffIDByUid(userInfoMapper.getByUserName(username).getUid()));
			modelAndView.addObject("ID",staffInfo.getStaff_id());
			modelAndView.addObject("Name",staffInfo.getStaff_name());
			modelAndView.addObject("Gender",staffInfo.getGender());
			modelAndView.addObject("Tel",staffInfo.getTel());
			modelAndView.addObject("Address",staffInfo.getAddress());
			modelAndView.addObject("Department",staffInfo.getDept());
			modelAndView.addObject("Position",staffInfo.getPosition());
		}
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		return modelAndView;
	}

	@RequestMapping(value="/profile", method=RequestMethod.POST)
	@RequiresUser
	public ModelAndView toChangePassword(HttpServletRequest request)
	{
		return getChangePasswd(request);
	}

	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	@RequiresUser
	public ModelAndView getChangePasswd(HttpServletRequest request)
	{
		CryptoUtil cryptoUtil=new CryptoUtil();
		Pair<RSAPrivateKey,RSAPublicKey> keyPair=cryptoUtil.generateRSAKey();
//		session.setAttribute("LoginPublicKey",cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));
		request.getSession().setAttribute("CP_PublicKey",org.apache.commons.codec.binary.Base64
				.encodeBase64String(keyPair.getValue().getEncoded()));
		request.getSession().setAttribute("CP_PublicKeyOriginal",keyPair.getValue());
		request.getSession().setAttribute("CP_PrivateKey",keyPair.getKey());


		System.out.println("From getChangePasswd: PUBLIC KEY: "+org.apache.commons.codec.binary.Base64
				.encodeBase64String(keyPair.getValue().getEncoded()));
		System.out.println("From getChangePasswd: PRIVATE KEY: "+org.apache.commons.codec.binary.Base64
				.encodeBase64String(keyPair.getKey().getEncoded()));


		ModelAndView modelAndView=new ModelAndView("changePassword");
		String username=(String)request.getSession().getAttribute("username");
		String Msg="";
		modelAndView.addObject("username",username);
		modelAndView.addObject("msg",Msg);
		return modelAndView;
	}


	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	@RequiresUser
	public ModelAndView postChangePasswd(HttpServletRequest request)
	{
		//TODO 注入RSA


		HttpSession session=request.getSession();
		CryptoUtil cryptoUtil=new CryptoUtil();
		PrivateKey privateKey=(PrivateKey)session.getAttribute("CP_PrivateKey");
		PublicKey publicKey=(PublicKey)session.getAttribute("CP_PublicKeyOriginal");


		String username=(String)session.getAttribute("username");
		String oldPassword=request.getParameter("oldPassword");
		String newPassword=request.getParameter("newPassword");
		String newPasswordConfirm=request.getParameter("newPasswordConfirm");

		System.out.println("From postChangePasswd: INITIAL OLDPASSWORD: "+oldPassword);
		System.out.println("From postChangePasswd: INITIAL NEWPASSWORD: "+newPassword);
		System.out.println("From postChangePasswd: INITIAL NEWPASSWORDCONFIRM: "+newPasswordConfirm);


		oldPassword=cryptoUtil.RSADecrypt(privateKey,oldPassword);
		newPassword=cryptoUtil.RSADecrypt(privateKey,newPassword);
		newPasswordConfirm=cryptoUtil.RSADecrypt(privateKey,newPasswordConfirm);


		UserInfo userInfo=userInfoMapper.getByUserName(username);

		//先验证旧的密码
		String alg="SHA-512";
		int iter=5;
		String Msg="";
		String verification=cryptoUtil.getHashedPassword(oldPassword,username,userInfo.getSalt());

		if(!verification.equals(userInfo.getUser_password()))
		{
			Msg="Original Password Incorrect.";
			System.out.println("From postChangePasswd :"+Msg);
			System.out.println("From postChangePasswd :old:"+userInfo.getUser_password());
			System.out.println("From postChangePasswd :new:"+verification);
		}
		else if(!newPassword.equals(newPasswordConfirm))
		{
			Msg="New Password Not Match.";
			System.out.println("From postChangePasswd :"+Msg);
		}
		else
		{
			String newSalt=cryptoUtil.getRandomString(128);
			String newCrd=cryptoUtil.getHashedPassword(newPassword,username,newSalt);
			userInfoMapper.updatePassword(userInfoService.getUidByUserName(username),(String)newCrd,newSalt);
			Msg="Success";
			System.out.println("From postChangePasswd :"+Msg);


			return logout(request.getSession());
		}


		return getChangePasswd(request).addObject("msg",Msg);
	}


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
	private TicketDao ticketDao;

	//	@ApiOperation(value="测试",notes="仅作为测试用")
	@RequestMapping(value="/ticket/list", method=RequestMethod.GET)
	public ModelAndView temp()
	{
		List<Ticket> testLists=ticketDao.getAll();
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
	public ModelAndView Index(HttpSession session)
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

		WeatherRequest weatherRequest = new WeatherRequest();
		Map<String, Object> weather = null;
		String weatherJSON = weatherRequest.getWeather();
		weather = weatherRequest.parseJSONToWeather(weatherJSON);

		String cond = weather.get("cond_txt_d").toString();
		String cond_icon = "";
		if (cond.equals("晴")){
			cond_icon = "fa fa-sun-o fa-3x";
		}else if (cond.indexOf("雪")>=0){
			cond_icon = "fa fa-asterisk fa-3x";
		}else if (cond.indexOf("雨")>=0){
			cond_icon = "fa fa-tint fa-3x";
		}else{
			cond_icon = "fa fa-cloud fa-3x";
		}

		String tmp_min = weather.get("tmp_min").toString();
		String tmp_max = weather.get("tmp_max").toString();
		String pop = weather.get("pop").toString();
		String hum = weather.get("hum").toString();
		String wind_dir = weather.get("wind_dir").toString();
		String weatherData = "最低温："+tmp_min+"°C  最高温："+tmp_max+"°C  降水概率："+pop+"%  相对湿度："+hum+"%  风向："+wind_dir;

		Map<String, Object> lifestyle = weatherRequest.parseJSONToLifeStyle(weatherRequest.getLifeStyle());
		String comf = "舒适度指数："+lifestyle.get("comf").toString();
		String drsg = "穿衣指数："+lifestyle.get("drsg").toString();
		String flu = "感冒指数："+lifestyle.get("flu").toString();
		String sport = "运动指数："+lifestyle.get("sport").toString();
		String air = "空气指数："+lifestyle.get("air").toString();

		modelAndView.addObject("cond_icon", cond_icon);
		modelAndView.addObject("weatherData", weatherData);
		modelAndView.addObject("cond_txt_d", weather.get("cond_txt_d").toString());
		modelAndView.addObject("comf",comf);
		modelAndView.addObject("drsg",drsg);
		modelAndView.addObject("flu",flu);
		modelAndView.addObject("sport",sport);
		modelAndView.addObject("air",air);
		modelAndView.addObject("username",username);
		modelAndView.addObject("roleName",roleName);
		modelAndView.addObject("date",date);

		return modelAndView;
	}
}
