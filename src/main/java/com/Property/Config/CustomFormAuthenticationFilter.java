package com.Property.Config;

import com.Property.Utility.CryptoUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;
import java.util.Map;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter
{

	@Override
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception
	{
		System.out.println("From AuthenticationFilter: Login Injection Started.");
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		HttpSession session=httpServletRequest.getSession();
		CryptoUtil cryptoUtil=new CryptoUtil();
		PrivateKey privateKey=(PrivateKey)session.getAttribute("LoginPrivateKey");
		PublicKey publicKey=(PublicKey)session.getAttribute("LoginPublicKeyOriginal");

		String username=request.getParameter("username");
		String passwd=request.getParameter("password");

		System.out.println("From AuthenticationFilter: INITIAL USERNAME: "+username);
		System.out.println("From AuthenticationFilter: INITIAL PASSWORD: "+passwd);

		passwd=cryptoUtil.RSADecrypt(privateKey,passwd);

		username=cryptoUtil.RSADecrypt(privateKey,username);


		System.out.println("From AuthenticationFilter: INJECT USERNAME: "+username);
		System.out.println("From AuthenticationFilter: INJECT PASSWORD: "+passwd);

		session.setAttribute("username",username);


		AuthenticationToken token=this.createToken(username,passwd,request,response);

//		AuthenticationToken token=this.createToken(request,response);
		if(token==null)
		{
			String msg="createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		else
		{
			try
			{
				Subject subject=this.getSubject(request,response);

				subject.login(token);
				System.out.println("From AuthenticationFilter: Login Injection Ended. -Success");
				return this.onLoginSuccess(token,subject,request,response);
			}
			catch(AuthenticationException e)
			{
				System.out.println("From AuthenticationFilter: Login Injection Ended. -Fail");
				return this.onLoginFailure(token,e,request,response);
			}
		}
	}

}
