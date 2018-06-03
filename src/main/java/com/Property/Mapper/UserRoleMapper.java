package com.Property.Mapper;

import com.Property.Entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserRoleMapper
{
	List<UserRoleMapper> getAll();

	UserRole getByID(String inputID);

}
