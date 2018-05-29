package com.Property.Config;

import com.Property.Entity.SysPermission;
import com.Property.Entity.UserInfo;
import com.Property.Service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class ShiroRealmConfig extends AuthorizingRealm
{
	@Resource
	private UserInfoService userInfoService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
	{
		System.out.println("From RealmConfig: Authorization Started.");
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		UserInfo userInfo=(UserInfo)principalCollection.getPrimaryPrincipal();
		authorizationInfo.addRole(userInfoService.getUserRole(userInfo.getUid()).getRole_name());
		for(SysPermission sysPermission : userInfoService.getUserPermission(userInfo.getUid()))
		{
			authorizationInfo.addStringPermission(sysPermission.getPermission());
			System.out.println("From RealmConfig: "+sysPermission.getPermission());
		}
		System.out.println("From RealmConfig: Authorization Ended.");
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException
	{
		System.out.println("From RealmConfig: Authentication Started.");
		String userName=(String)authenticationToken.getPrincipal();
		UserInfo userInfo=userInfoService.getUserInfo(userName);
		if(userInfo!=null)
			System.out.println("From RealmConfig: USERID: "+userInfo.getUid());
		else
		{
			System.out.println("From RealmConfig: Authentication Ended. -Fail");
			return null;
		}
		//此处可以对密码进行解密
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(userInfo,userInfo
				.getUser_password(),ByteSource.Util.bytes(userInfo.getCredentialsSalt()),getName());
		System.out.println("From RealmConfig: Authentication Ended. -Success");
		return authenticationInfo;
	}
}
