package com.Property.Service;

import com.Property.Entity.SysPermission;
import com.Property.Entity.SysRole;
import com.Property.Entity.UserInfo;

import java.util.List;

public interface UserInfoService
{
	int getUidByUserName(String userName);
	UserInfo getUserInfo(String userName);
	SysRole getUserRole(int uid);
	List<SysPermission> getUserPermission(int uid);
	void updatePassword(int uid,String newPassword,String newSalt);
}