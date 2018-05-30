package com.Property.Service.Impl;

import com.Property.Dao.ProprietorDao;
import com.Property.Dao.StaffDao;
import com.Property.Domain.Proprietor;
import com.Property.Domain.Staff;
import com.Property.Entity.SysPermission;
import com.Property.Entity.SysRole;
import com.Property.Entity.UserInfo;
import com.Property.Mapper.SysPermissionMapper;
import com.Property.Mapper.SysRoleMapper;
import com.Property.Mapper.UserInfoMapper;
import com.Property.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

	@Autowired
	StaffDao staffDao;

	@Autowired
	ProprietorDao proprietorDao;

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
		List<SysPermission> tempParentPermission=new ArrayList<>();
		if(result!=null)
		{
			for(int i=0;i<result.size();)
			{
				List<SysPermission> ChildPermission=sysPermissionMapper.getChildPermission(result.get(i).getPerm_id());
				if(ChildPermission.size()!=0)
				{
					tempParentPermission.add(result.get(i));
					result.remove(i);
					result.addAll(ChildPermission);
				}
				else
					i++;
			}
			result.addAll(tempParentPermission);
		}
		return result;
	}

	@Override
	public Staff getStaffInfoByUID(int inputUID)
	{
		return staffDao.getSelfInfo(userInfoMapper.getStaffIDByUid(inputUID));
	}

	@Override
	public Proprietor getPrprtInfoByUID(int inputUID)
	{
		return proprietorDao.getSelfInfo(userInfoMapper.getStaffIDByUid(inputUID));
	}

	@Override
	public SysRole getUserRole(int uid)
	{
		return sysRoleMapper.getByUid(uid);
	}

	@Override
	public void updatePassword(int uid,String newPassword,String newSalt)
	{
		userInfoMapper.updatePassword(uid,newPassword,newSalt);
		System.out.println("Update Success");
	}
}
