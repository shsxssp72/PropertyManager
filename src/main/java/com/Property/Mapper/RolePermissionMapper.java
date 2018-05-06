package com.Property.Mapper;

import com.Property.Entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper
{
	List<RolePermission> getAll();
}
