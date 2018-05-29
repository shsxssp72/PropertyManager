package com.Property.Service;

import com.Property.Domain.Staff;
import com.Property.Domain.Suggestion;
import org.springframework.stereotype.Component;

import java.sql.Date;

public interface ReceptionistService
{

	/*面对面缴费*/
	int updateChargingSituation(String collector_id,Date charge_date,String fee_id);

	/*面对面意见*/
	int createAdvice(Suggestion suggestion);

	/*查看个人信息*/
	Staff getSelfInfo(String id);
}
