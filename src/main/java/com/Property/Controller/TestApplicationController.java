package com.Property.Controller;

import com.Property.Entity.Ticket;
import com.Property.Mapper.TicketMapper;
import com.Property.Service.Impl.UserInfoServiceImpl;
import com.Property.Service.UserInfoService;
import com.Property.Utility.CryptoUtil;
import com.Property.Utility.Pair;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Service.GuardService;
import com.Property.Service.ProprietorService;
import com.Property.Utility.AssignAlgorithmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestApplicationController
{
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String toLogin(Model model,HttpSession session)
	{
		CryptoUtil cryptoUtil=new CryptoUtil();
		Pair<RSAPrivateKey,RSAPublicKey> keyPair=cryptoUtil.generateRSAKey();
//		session.setAttribute("LoginPublicKey",cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));
		session.setAttribute("LoginPublicKey",org.apache.commons.codec.binary.Base64
				.encodeBase64String(keyPair.getValue().getEncoded()));
		session.setAttribute("LoginPublicKeyOriginal",keyPair.getValue());
		session.setAttribute("LoginPrivateKey",keyPair.getKey());


//		session.setAttribute("enc_data",cryptoUtil.RSAEncrypt(keyPair.getValue(),"aeiuo"));


//		System.out.println("PKCS1: "+cryptoUtil.getPKCS1PublicKey(keyPair.getValue()));


		model.addAttribute("title","Login");
		return "/login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String LoginVerify(HttpServletRequest request,Map<String,Object> map) throws Exception
	{
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
//			else if("kaptchaValidateFailed".equals(exception))
//			{
//				System.out.println("kaptchaValidateFailed -- > 验证码错误");
//				msg="kaptchaValidateFailed -- > 验证码错误";
//			}
			else
			{
				msg="Inner Error.";
				errorMessage="Unexpected Error: "+exception;
				System.out.println("From LoginVerify: "+errorMessage);
			}
		}
		map.put("msg",msg);
		System.out.println("From LoginVerify: Login Verify Ended.");
		return "/login";
	}

	@RequestMapping("/403")
	public String PermissionDenied()
	{
		return "../public/error/403";
	}
	@Autowired
	private TicketDao ticketMapper;
//	private ProprietorService propertyService;

	@Autowired
	private SubareaDao subareaDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private GuardService guardService;
	@Autowired
	private ChargingItemDao chargingItemDao;
	@Autowired
	private ProprietorDao proprietorDao;
	@Autowired
	private ChargingSituationDao chargingSituationDao;



	@RequestMapping("/Test")
	public ModelAndView index()
	{
		List<Ticket> testLists=ticketMapper.getAll();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);

//		List<Fee> feeList = propertyService.getFee();
//		ModelAndView modelAndView = new ModelAndView("dashboard_Test");
//		modelAndView.addObject("feeList",feeList);
		return modelAndView;
	}

	@RequestMapping("/Assign")
	public void assign(){

		int task_num = 0;
		int rev_num = 0;
		List<Subarea> subareas = subareaDao.getAll();
		String[] task = new String[subareas.size()];
		for(Subarea s : subareas){
			task[task_num] = s.getSubarea_id();
			task_num++;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("position", "guard");
		List<Staff> staff = staffDao.getStaffbyParams(params);
		/*String[] guard = new String[staff.size()];
		for (Staff s : staff){
			guard[rev_num] = s.getStaff_id();
			rev_num++;
		}*/

		String[][] rev_task = new String[staff.size()][3];
		for (Staff s : staff){
			rev_task[rev_num][0] = s.getStaff_id();
			rev_task[rev_num][1] = String.valueOf(guardService.tbdTaskCount(s.getStaff_id()));
			rev_num++;
		}

		AssignAlgorithmUtil.taskAllocation(task_num, rev_num, rev_task);

		int k=0;
		for (int i =0; i<rev_num; i++){
			for (int j =0; j<Integer.parseInt(rev_task[i][2]); j++){
				System.out.println("insert into dailyTask values "+rev_task[i][0]+" "+task[k]);
				k++;
			}
		}
	}

	@RequestMapping("/Charging")
	public void charging(){
		List<ChargingItem> chargingItems = chargingItemDao.getAll();
		List<Proprietor> proprietors = proprietorDao.getAll();

		for (ChargingItem c : chargingItems){
			for (Proprietor p : proprietors){
				Date now = new Date();
				Date latest = chargingSituationDao.getLatestPayment(p.getPrprt_id(), c.getItem_id());
				if (latest==null){
				}else{
					long nd = 1000 * 24 * 60 * 60;
					long diff = latest.getTime() - now.getTime();
					long day = diff / nd;
					System.out.println(latest.toString());
					System.out.println(now.toString());
					System.out.println(day);
				}
			}
		}
	}

}
