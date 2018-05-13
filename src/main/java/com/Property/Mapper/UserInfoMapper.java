package com.Property.Mapper;

import com.Property.Entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserInfoMapper
{
	List<UserInfo> getAll();

	UserInfo getByUserName(String userName);

	void updatePassword(@Param("uid")int uid,@Param("newPassword")String newPassword,@Param("newSalt")String newSalt);

}
