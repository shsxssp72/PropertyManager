package com.Property.Mapper;

import com.Property.Entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper
{
	List<SysRole> getAll();
	SysRole getByUid(int uid);
}
