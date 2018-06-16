package com.Property.Dao;

import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Fee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface FeeDao
{
	List<Fee> getAll();

	Fee getByID(String inputID);

	List<Fee> getFee();

	/*根据参数值查询*/
	List<Fee> getFeebyParams(Map<String, Object> params);

	int addFee(Fee fee);

	int deleteFee(String id);

	int updateFee(Fee fee);

	//用于判定ID是否重复
	int getIdCount(String inputID);

	//获取工单收费项
	String getTicketItem();
}
