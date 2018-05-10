package com.Property.Controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
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
//		Subject currentUser=SecurityUtils.getSubject();
//		String username =request.getParameter("username");
//		String passwd=request.getParameter("password");
//		UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,passwd);
//		System.out.println("*******************************************");
//
//		currentUser.login(usernamePasswordToken);
//		System.out.println("*******************************************");

		String exception=(String)request.getAttribute("shiroLoginFailure");
		System.out.println("exception="+exception);
		String msg="";
		if(exception!=null)
		{
			if(UnknownAccountException.class.getName().equals(exception))
			{
				System.out.println("UnknownAccountException -- > 账号不存在：");
				msg="UnknownAccountException -- > 账号不存在：";
			}
			else if(IncorrectCredentialsException.class.getName().equals(exception))
			{
				System.out.println("IncorrectCredentialsException -- > 密码不正确：");
				msg="IncorrectCredentialsException -- > 密码不正确：";
			}
			else if("kaptchaValidateFailed".equals(exception))
			{
				System.out.println("kaptchaValidateFailed -- > 验证码错误");
				msg="kaptchaValidateFailed -- > 验证码错误";
			}
			else
			{
				msg="else >> "+exception;
				System.out.println("else -- >"+exception);
			}
		}
		map.put("msg",msg);
		return "/login";
	}

	@RequestMapping("/403")
	public String PermissionDenied()
	{
		return "../public/error/403";
	}


}
