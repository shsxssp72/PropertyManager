package com.Property.Service;

import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Proprietor;
import com.Property.Domain.Suggestion;
import com.Property.Domain.Ticket;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

/*
业主业务逻辑接口类
*/
public interface ProprietorService
{

	/*未缴费记录*/
	List<ChargingSituation> getPayment(String id);

	/*缴费历史*/
	List<ChargingSituation> getPaymentHistory(String id);

	/*报修*/
	int prprtCallRepair(Ticket ticket);

	/*查看报修历史*/
	List<Ticket> getRepairHistory(String id);

	/*评价维修质量*/
	int updateRepairFB(int feedback,String prprt_id,String ticket_id);

	/*提出意见*/
	int giveAdvice(Suggestion suggestion);

	/*查看意见历史*/
	List<Suggestion> getAdviceHistory(String id);

	/*查看个人信息*/
	Proprietor getSelfInfo(String id);

	/*修改联系电话*/
	int alterTel(String tel,String id);

	/*根据参数值查询缴费记录*/
	List<ChargingSituation> getPaymentbyParams(Map<String,Object> params);
}
