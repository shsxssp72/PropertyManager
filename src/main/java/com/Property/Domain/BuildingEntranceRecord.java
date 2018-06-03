package com.Property.Domain;

import java.sql.Timestamp;

public class BuildingEntranceRecord
{
	private String entrance_record_id;
	private String prprt_id;
	private String building_id;
	private Timestamp access_time;
	private String verify_type;

	public String getEntrance_record_id()
	{
		return entrance_record_id;
	}

	public void setEntrance_record_id(String entrance_record_id)
	{
		this.entrance_record_id = entrance_record_id;
	}

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id = prprt_id;
	}

	public String getBuilding_id()
	{
		return building_id;
	}

	public void setBuilding_id(String building_id)
	{
		this.building_id=building_id;
	}

	public Timestamp getAccess_time()
	{
		return access_time;
	}

	public void setAccess_time(Timestamp access_time)
	{
		this.access_time = access_time;
	}

	public String getVerify_type()
	{
		return verify_type;
	}

	public void setVerify_type(String verify_type)
	{
		this.verify_type = verify_type;
	}
}
