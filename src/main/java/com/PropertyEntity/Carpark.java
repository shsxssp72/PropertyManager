package com.PropertyEntity;

import java.sql.Timestamp;

public class Carpark
{
	private String carpark_id;
	private String subarea_id;
	private String owner_id;
	private Timestamp valid_term;

	public String getCarpark_id()
	{
		return carpark_id;
	}

	public void setCarpark_id(String carpark_id)
	{
		this.carpark_id = carpark_id;
	}

	public String getSubarea_id()
	{
		return subarea_id;
	}

	public void setSubarea_id(String subarea_id)
	{
		this.subarea_id = subarea_id;
	}

	public String getOwner_id()
	{
		return owner_id;
	}

	public void setOwner_id(String owner_id)
	{
		this.owner_id = owner_id;
	}

	public Timestamp getValid_term()
	{
		return valid_term;
	}

	public void setValid_term(Timestamp valid_term)
	{
		this.valid_term = valid_term;
	}
}
