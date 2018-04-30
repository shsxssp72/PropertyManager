package com.Property.Entity;

import java.sql.Timestamp;

public class CarIORecord
{
	private String io_record_id;
	private String plate_number;
	private String prprt_id;
	private Timestamp record_in_time;
	private Timestamp record_out_time;
	private double price;

	public String getIo_record_id()
	{
		return io_record_id;
	}

	public void setIo_record_id(String io_record_id)
	{
		this.io_record_id=io_record_id;
	}

	public String getPlate_number()
	{
		return plate_number;
	}

	public void setPlate_number(String plate_number)
	{
		this.plate_number=plate_number;
	}

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id=prprt_id;
	}

	public Timestamp getRecord_in_time()
	{
		return record_in_time;
	}

	public void setRecord_in_time(Timestamp record_in_time)
	{
		this.record_in_time=record_in_time;
	}

	public Timestamp getRecord_out_time()
	{
		return record_out_time;
	}

	public void setRecord_out_time(Timestamp record_out_time)
	{
		this.record_out_time=record_out_time;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price=price;
	}
}
