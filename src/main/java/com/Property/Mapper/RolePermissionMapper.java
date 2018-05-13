package com.Property.Mapper;

import com.Property.Entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RolePermissionMapper
{
	List<RolePermission> getAll();
}
