package com.PropertyEntity;

import java.sql.Timestamp;

public class Proprietor
{
	private String prprt_id;
	private String prprt_name;
	private String gender;
	private String tel;
	private Timestamp birthday;
	private String aprt_building;
	private int aprt_floor;
	private int aprt_room_num;

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id = prprt_id;
	}

	public String getPrprt_name()
	{
		return prprt_name;
	}

	public void setPrprt_name(String prprt_name)
	{
		this.prprt_name = prprt_name;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public Timestamp getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Timestamp birthday)
	{
		this.birthday = birthday;
	}

	public String getAprt_building()
	{
		return aprt_building;
	}

	public void setAprt_building(String aprt_building)
	{
		this.aprt_building = aprt_building;
	}

	public int getAprt_floor()
	{
		return aprt_floor;
	}

	public void setAprt_floor(int aprt_floor)
	{
		this.aprt_floor = aprt_floor;
	}

	public int getAprt_room_num()
	{
		return aprt_room_num;
	}

	public void setAprt_room_num(int aprt_room_num)
	{
		this.aprt_room_num = aprt_room_num;
	}
}
