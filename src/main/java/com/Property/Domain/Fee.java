package com.Property.Domain;

import java.sql.Timestamp;

public class Fee
{
	private String fee_id;
	private String item_id;
	private Timestamp start_date;
	private Timestamp end_date;
	private double price;

	private ChargingItem chargingItem;		//新增chargingItem用于resultMap数据绑定

	public String getFee_id()
	{
		return fee_id;
	}

	public void setFee_id(String fee_id)
	{
		this.fee_id = fee_id;
	}

	public String getItem_id()
	{
		return item_id;
	}

	public void setItem_id(String item_id)
	{
		this.item_id = item_id;
	}

	public Timestamp getStart_date()
	{
		return start_date;
	}

	public void setStart_date(Timestamp start_date)
	{
		this.start_date = start_date;
	}

	public Timestamp getEnd_date()
	{
		return end_date;
	}

	public void setEnd_date(Timestamp end_date)
	{
		this.end_date = end_date;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public ChargingItem getChargingItem() {
		return chargingItem;
	}

	public void setChargingItem(ChargingItem chargingItem) {
		this.chargingItem = chargingItem;
	}
}
