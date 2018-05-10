package com.Property.Domain;

import java.sql.Timestamp;

public class ChargingSituation
{
	private String situation_id;
	private String fee_id;
	private String prprt_id;
	private String collector_id;
	private Timestamp charge_date;

	private Fee fee;					//新增属性用于resultMap中的联合查询
	private Proprietor proprietor;
	private Staff staff;

	public String getSituation_id()
	{
		return situation_id;
	}

	public void setSituation_id(String situation_id)
	{
		this.situation_id = situation_id;
	}

	public String getFee_id()
	{
		return fee_id;
	}

	public void setFee_id(String fee_id)
	{
		this.fee_id = fee_id;
	}

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id = prprt_id;
	}

	public String getCollector_id()
	{
		return collector_id;
	}

	public void setCollector_id(String collector_id)
	{
		this.collector_id = collector_id;
	}

	public Timestamp getCharge_date()
	{
		return charge_date;
	}

	public void setCharge_date(Timestamp charge_date)
	{
		this.charge_date = charge_date;
	}

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public Proprietor getProprietor() {
		return proprietor;
	}

	public void setProprietor(Proprietor proprietor) {
		this.proprietor = proprietor;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}
