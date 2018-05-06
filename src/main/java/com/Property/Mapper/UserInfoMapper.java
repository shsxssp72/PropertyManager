package com.Property.Mapper;

import com.Property.Entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper
{
	List<UserInfo> getAll();
	UserInfo getByUserName(String userName);

}
