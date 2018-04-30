package com.Property.Mapper;

import com.Property.Entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper
{
	List<Department> getAll();
}
