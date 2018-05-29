package com.Property.Service;

import com.Property.Domain.CarIORecord;
import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Staff;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface AccountantService
{

	/*查看物业缴费记录*/
	List<ChargingSituation> getAllCharging();

	/*查看外来车辆出入缴费记录*/
	List<CarIORecord> getExternal();

	/*查看个人信息*/
	Staff getSelfInfo(String id);

	/*根据参数值查询物业缴费记录*/
	List<ChargingSituation> getPaymentbyParams(Map<String,Object> params);
}
