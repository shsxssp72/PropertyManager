package com.Property.Mapper;

import com.Property.Entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysRoleMapper
{
	List<SysRole> getAll();

	SysRole getByUid(int uid);
}
