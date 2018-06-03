package com.Property.Mapper;

import com.Property.Entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface SysPermissionMapper
{
	List<SysPermission> getAll();

	SysPermission getByID(String inputID);

	List<SysPermission> getByRoleId(int roleId);

	List<SysPermission> getChildPermission(int permId);

}
