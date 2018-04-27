package com.Property.Entity;

public class Facilities
{
	private String fclt_id;
	private String fclt_type;
	private String subarea_id;
	private String building_id;
	private int floor;
	private String location;

	public String getFclt_id()
	{
		return fclt_id;
	}

	public void setFclt_id(String fclt_id)
	{
		this.fclt_id = fclt_id;
	}

	public String getFclt_type()
	{
		return fclt_type;
	}

	public void setFclt_type(String fclt_type)
	{
		this.fclt_type = fclt_type;
	}

	public String getSubarea_id()
	{
		return subarea_id;
	}

	public void setSubarea_id(String subarea_id)
	{
		this.subarea_id = subarea_id;
	}

	public String getBuilding_id()
	{
		return building_id;
	}

	public void setBuilding_id(String building_id)
	{
		this.building_id = building_id;
	}

	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor)
	{
		this.floor = floor;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
}
