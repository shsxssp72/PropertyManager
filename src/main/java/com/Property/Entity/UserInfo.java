package com.Property.Entity;

import java.io.Serializable;

public class UserInfo implements Serializable
{
	private int uid;
	private String user_name;
	private String display_name;
	private String user_password;
	private String salt;
	private int state;
	private String prprt_id;
	private String staff_id;

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid=uid;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name=user_name;
	}

	public String getDisplay_name()
	{
		return display_name;
	}

	public void setDisplay_name(String display_name)
	{
		this.display_name=display_name;
	}

	public String getUser_password()
	{
		return user_password;
	}

	public void setUser_password(String user_password)
	{
		this.user_password=user_password;
	}

	public String getSalt()
	{
		return salt;
	}

	public void setSalt(String salt)
	{
		this.salt=salt;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state=state;
	}

	public String getCredentialsSalt()
	{
		return this.user_name+this.salt;
	}

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id=prprt_id;
	}

	public String getStaff_id()
	{
		return staff_id;
	}

	public void setStaff_id(String staff_id)
	{
		this.staff_id=staff_id;
	}
}
