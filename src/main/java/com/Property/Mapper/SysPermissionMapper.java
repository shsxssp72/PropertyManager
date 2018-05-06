package com.Property.Mapper;

import com.Property.Entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper
{
	List<SysPermission> getAll();
	List<SysPermission> getByRoleId(int roleId);
	List<SysPermission> getChildPermission(int permId);
}
