package com.Property.Service.Impl;

import com.Property.Entity.SysPermission;
import com.Property.Entity.SysRole;
import com.Property.Entity.UserInfo;
import com.Property.Mapper.SysPermissionMapper;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService
{
	@Autowired
	UserInfoMapper userInfoMapper;

	@Autowired
	SysRoleMapper sysRoleMapper;

	@Autowired
	SysPermissionMapper sysPermissionMapper;

	@Override
	public int getUidByUserName(String userName)
	{
		UserInfo userInfo=userInfoMapper.getByUserName(userName);
		return userInfo.getUid();
	}

	@Override
	public UserInfo getUserInfo(String userName)
	{
		return userInfoMapper.getByUserName(userName);
	}

	@Override
	public List<SysPermission> getUserPermission(int uid)
	{
		SysRole role=sysRoleMapper.getByUid(uid);
		List<SysPermission> result=sysPermissionMapper.getByRoleId(role.getRole_id());
		for(SysPermission r : result)
		{
			result.addAll(sysPermissionMapper.getChildPermission(r.getPerm_id()));
		}
		return result;
	}

	@Override
	public SysRole getUserRole(int uid)
	{
		return sysRoleMapper.getByUid(uid);
	}
}
