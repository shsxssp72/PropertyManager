package com.Property.Entity;

import java.io.Serializable;

public class SysPermission implements Serializable
{
	private int perm_id;
	private String perm_name;
	private String resource_type;
	private String permit_url;
	private String permission;
	private int parent_id;
	private String parent_ids;
	private boolean is_available;

	public int getPerm_id()
	{
		return perm_id;
	}

	public void setPerm_id(int perm_id)
	{
		this.perm_id=perm_id;
	}

	public String getPerm_name()
	{
		return perm_name;
	}

	public void setPerm_name(String perm_name)
	{
		this.perm_name=perm_name;
	}

	public String getResource_type()
	{
		return resource_type;
	}

	public void setResource_type(String resource_type)
	{
		this.resource_type=resource_type;
	}

	public String getPermit_url()
	{
		return permit_url;
	}

	public void setPermit_url(String permit_url)
	{
		this.permit_url=permit_url;
	}

	public String getPermission()
	{
		return permission;
	}

	public void setPermission(String permission)
	{
		this.permission=permission;
	}

	public int getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(int parent_id)
	{
		this.parent_id=parent_id;
	}

	public String getParent_ids()
	{
		return parent_ids;
	}

	public void setParent_ids(String parent_ids)
	{
		this.parent_ids=parent_ids;
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
