package com.PropertyEntity;

import java.sql.Timestamp;

public class CarIORecord
{
	private String io_record_id;
	private String plate_number;
	private String prprt_id;
	private String record_type;
	private Timestamp record_time;

	public String getIo_record_id()
	{
		return io_record_id;
	}

	public void setIo_record_id(String io_record_id)
	{
		this.io_record_id = io_record_id;
	}

	public String getPlate_number()
	{
		return plate_number;
	}

	public void setPlate_number(String plate_number)
	{
		this.plate_number = plate_number;
	}

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id = prprt_id;
	}

	public String getRecord_type()
	{
		return record_type;
	}

	public void setRecord_type(String record_type)
	{
		this.record_type = record_type;
	}

	public Timestamp getRecord_time()
	{
		return record_time;
	}

	public void setRecord_time(Timestamp record_time)
	{
		this.record_time = record_time;
	}
}
