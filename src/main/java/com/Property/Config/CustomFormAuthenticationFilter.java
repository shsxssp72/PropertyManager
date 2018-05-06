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
import java.util.Iterator;
import java.util.Map;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter
{

	@Override
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception
	{
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		HttpSession session=httpServletRequest.getSession();
		Key key=(Key)session.getAttribute("LoginKey");
		CryptoUtil cryptoUtil=new CryptoUtil();
		String username=request.getParameter("username");
		String passwd=request.getParameter("password");

//				passwd=cryptoUtil.Base64Decoder(passwd);
//				passwd=cryptoUtil.AESDecrypt(passwd,key);
//
//				username=cryptoUtil.Base64Decoder(username);
//				username=cryptoUtil.AESDecrypt(username,key);


		username="Test";
		passwd="111";
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

		Map map =request.getParameterMap();
		Iterator iter=map.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry entry=(Map.Entry)iter.next();
			Object key1=entry.getKey();
			Object val=entry.getValue();
			System.out.println(key1+":"+val);
		}
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
					return this.onLoginSuccess(token,subject,request,response);
				}
				catch(AuthenticationException var5)
				{
					return this.onLoginFailure(token,var5,request,response);
				}
			}
		}

	}
