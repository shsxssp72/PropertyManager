package com.Property.Dao;

import com.Property.Domain.ChargingSituation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ChargingSituationDao
{
	String getChargingItemNameByCSID(String inputCSID);

	List<ChargingSituation> getUnfinishedByUserId(String inputUID);

	ChargingSituation getByID(String inputID);

	List<ChargingSituation> getAll();

	List<ChargingSituation> getPayment(String id);

	java.util.Date getLatestPayment(@Param("id") String id, @Param("item_id") String item_id);

	List<ChargingSituation> getPaymentHistory(String id);

	List<ChargingSituation> getAllCharging();

	int updateChargingSituation(@Param("collector_id") String collector_id, @Param("charge_date") Date charge_date, @Param("fee_id") String fee_id);

	/*根据参数值查询*/
	List<ChargingSituation> getPaymentbyParams(Map<String, Object> params);

	int addChargingSituation(ChargingSituation chargingSituation);

	int deleteChargingSituation(String id);

	int updateChargingSituation(ChargingSituation chargingSituation);

//	int updateChargingSituation(@Param("chargingSituation_id") String chargingSituation_id,@Param("fee_id") String fee_id,
//								@Param("prprt_id") String prprt_id,@Param("collector_id") String collector_id,@Param("charge_date") Timestamp charge_date);


	//用于判定ID是否重复
	int getIdCount(String inputID);
}
