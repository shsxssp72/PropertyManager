package com.Property.Entity;

import java.io.Serializable;

public class SysRole implements Serializable
{
	private int role_id;
	private String role_name;
	private String descryption;
	private boolean is_available;

	public int getRole_id()
	{
		return role_id;
	}

	public void setRole_id(int role_id)
	{
		this.role_id=role_id;
	}

	public String getRole_name()
	{
		return role_name;
	}

	public void setRole_name(String role_name)
	{
		this.role_name=role_name;
	}

	public String getDescryption()
	{
		return descryption;
	}

	public void setDescryption(String descryption)
	{
		this.descryption=descryption;
	}

	public boolean isIs_available()
	{
		return is_available;
	}

	public void setIs_available(boolean is_available)
	{
		this.is_available=is_available;
	}
}
