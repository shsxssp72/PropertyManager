package com.Property.Dao;

import com.Property.Domain.OverhaulRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface OverhaulRecordDao
{
	List<OverhaulRecord> getAll();

	int finishOverhaul(@Param("overhaul_time") Timestamp overhaul_time, @Param("result") String result, @Param("overhaul_id") String overhaul_id);

	List<OverhaulRecord> tbdOverhaul(String id);

	List<OverhaulRecord> overhaulHistory(String id);

	int changeOverhaulHandler(@Param("new_handler") String new_handler, @Param("overhaul_id") String overhaul_id);

	/*根据参数值查询*/
	List<OverhaulRecord> getOverhaulbyParams(Map<String, Object> params);
}
