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


@Controller
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

}
