package com.Property.Dao;

import com.Property.Domain.DailyTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface DailyTaskDao
{
	List<DailyTask> getAll();

	int finishTask(@Param("result") String result, @Param("isException") Boolean isException, @Param("task_id") String task_id);

	List<DailyTask> getHistoryTask(String id);

	List<DailyTask> tbdTask(String id);

	int tbdTaskCount(String id);

	int changeTaskHandler(@Param("new_handler") String new_handler, @Param("task_id") String task_id);

	/*根据参数值查询*/
	List<DailyTask> getTaskbyParams(Map<String, Object> params);
}
