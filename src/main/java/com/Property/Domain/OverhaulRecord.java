package com.Property.Domain;

public class OverhaulRecord
{
	private String overhaul_id;
	private String fclt_id;
	private String overhaul_type;
	private String overhaul_time;
	private String overhaul_handler;
	private String overhaul_result;

	private Facilities facilities;			//新增属性用于resultMap中联合查询

	public String getOverhaul_id()
	{
		return overhaul_id;
	}

	public void setOverhaul_id(String overhaul_id)
	{
		this.overhaul_id = overhaul_id;
	}

	public String getFclt_id()
	{
		return fclt_id;
	}

	public void setFclt_id(String fclt_id)
	{
		this.fclt_id = fclt_id;
	}

	public String getOverhaul_type()
	{
		return overhaul_type;
	}

	public void setOverhaul_type(String overhaul_type)
	{
		this.overhaul_type = overhaul_type;
	}

	public String getOverhaul_time()
	{
		return overhaul_time;
	}

	public void setOverhaul_time(String overhaul_time)
	{
		this.overhaul_time = overhaul_time;
	}

	public String getOverhaul_handler()
	{
		return overhaul_handler;
	}

	public void setOverhaul_handler(String overhaul_handler)
	{
		this.overhaul_handler = overhaul_handler;
	}

	public String getOverhaul_result()
	{
		return overhaul_result;
	}

	public void setOverhaul_result(String overhaul_result)
	{
		this.overhaul_result = overhaul_result;
	}

	public Facilities getFacilities() {
		return facilities;
	}

	public void setFacilities(Facilities facilities) {
		this.facilities = facilities;
	}
}
