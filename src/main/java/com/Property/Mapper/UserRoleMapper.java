package com.Property.Mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper
{
	List<UserRoleMapper> getAll();

}
