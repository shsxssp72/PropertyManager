package com.Property.Domain;

public class Building
{
	private String building_id;
	private String subarea_id;
	private int max_floor;
	private int max_room_num;

	public String getBuilding_id()
	{
		return building_id;
	}

	public void setBuilding_id(String building_id)
	{
		this.building_id=building_id;
	}

	public String getSubarea_id()
	{
		return subarea_id;
	}

	public void setSubarea_id(String subarea_id)
	{
		this.subarea_id=subarea_id;
	}

	public int getMax_floor()
	{
		return max_floor;
	}

	public void setMax_floor(int max_floor)
	{
		this.max_floor=max_floor;
	}

	public int getMax_room_num()
	{
		return max_room_num;
	}

	public void setMax_room_num(int max_room_num)
	{
		this.max_room_num=max_room_num;
	}
}
