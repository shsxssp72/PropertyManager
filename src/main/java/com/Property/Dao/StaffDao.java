package com.Property.Dao;

import com.Property.Domain.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface StaffDao
{
	List<Staff> getAll();

	Staff getSelfInfo(String id);

	List<Staff> getDeptStaff(String dept);

	/*根据参数值查询*/
	List<Staff> getStaffbyParams(Map<String,Object> params);

	int addStaff(Staff staff);

	int deleteStaff(String id);

	int updateStaff(Staff staff);
}
