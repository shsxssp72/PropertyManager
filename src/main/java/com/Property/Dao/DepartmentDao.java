package com.Property.Dao;

import com.Property.Domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface DepartmentDao
{
	List<Department> getAll();

	Department getByID(String inputID);

	/*根据参数值查询*/
	List<Department> getDeptbyParams(Map<String,Object> params);

	int addDepartment(Department department);

	int deleteDepartment(String id);

	int updateDepartment(Department department);

	//用于判定ID是否重复
	int getIdCount(String inputID);
}
